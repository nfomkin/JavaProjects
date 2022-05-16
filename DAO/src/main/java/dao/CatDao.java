package dao;


import com.google.common.base.*;
import dto.*;
import entity.*;
import exception.*;
import java.sql.*;
import java.time.*;
import java.util.Optional;
import java.util.*;
import java.util.stream.*;
import util.*;

public class CatDao implements Dao<Cat, Long> {

  private static CatDao instance = new CatDao();
  private OwnerDao ownerDao = OwnerDao.getInstance();

  private static final String FIND_ALL = """
      Select id, name, birth_date, breed, color, owner_id
      From kotiki.cats
      """;

  private static final String FIND_BY_ID = FIND_ALL + "Where id = ?";

  private static final String UPDATE_SQL = """
      Update kotiki.cats
      Set name = ?, birth_date = ?, breed = ?, color = ?, owner_id = ?
      Where id = ? 
      """;

  private static final String DELETE_SQL = """
      Delete From kotiki.cats  
      Where id = ?
      """;

  private static final String INSERT_SQL = """
      Insert into kotiki.cats (name, birth_date, breed, color, owner_id) values (?, ?, ?, ?, ?)
      """;

  private static final String ADD_FRIENDSHIP_SQL = """
      Insert into kotiki.friendship (cat1_id, cat2_id) values (%d, %d)
      On conflict do nothing 
      """;

  private static final String DELETE_FRIENDSHIP_SQL = """
      Delete From kotiki.friendship
      Where cat1_id = %d or cat2_id = %d
      """;

  private CatDao() {
  }

  public static CatDao getInstance() {
    return instance;
  }

  private Cat buildCat(ResultSet resultSet) throws SQLException {
    List<Long> friends = new ArrayList<>();
    String sql = """
        Select cat1_id
        From kotiki.friendship
        Where cat2_id = ?
        Union 
        Select cat2_id
        From kotiki.friendship
        Where cat1_id = ?
        """;
    try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setLong(1, resultSet.getLong("id"));
      preparedStatement.setLong(2, resultSet.getLong("id"));

      var friendsSet = preparedStatement.executeQuery();
      while (friendsSet.next()) {
        friends.add(friendsSet.getLong(1));
      }

    }

    return Cat.builder().id(resultSet.getLong("id"))
        .birthDate((LocalDate) resultSet.getObject("birth_date"))
        .breed(resultSet.getString("breed")).name(resultSet.getString("name"))
        .color(resultSet.getString("color"))
        .owner(ownerDao.findById(resultSet.getLong("owner_id")).orElseThrow())
        .friends(friends)
        .build();
  }

  public List<Cat> findAll(CatFilter catFilter) {
    List<Object> params = new ArrayList<>();
    List<String> whereSql = new ArrayList<>();

    var fields = catFilter.getClass().getDeclaredFields();
    Arrays.stream(fields).forEach(f -> f.setAccessible(true));
    for (var field : fields) {
      var fieldName = field.getName();
      if (!fieldName.equals("limit") && !fieldName.equals("offset")) {
        whereSql.add(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName));
        try {
          params.add(field.get(catFilter));
        } catch (IllegalAccessException e) {
          throw new CatDaoException(e);
        }
      }
    }

    String findSql = FIND_ALL + whereSql.stream().collect(Collectors.joining("=? and", "Where",
        String.format("Limit %d Offset %d", catFilter.limit(), catFilter.offset())));
    System.out.println(findSql);
    List<Cat> cats = new ArrayList<>();
    try (var connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(findSql)) {
      for (int i = 0; i < params.size(); i++) {
        preparedStatement.setObject(i + 1, params.get(i));
      }
      var resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        cats.add(buildCat(resultSet));
      }
    }
    catch (SQLException e) {
      e.printStackTrace();
    }
    return cats;
  }

  @Override
  public List<Cat> findAll() {
    List<Cat> cats = new ArrayList<>();
    try (var connection = ConnectionManager.get();
          var statement = connection.prepareStatement(FIND_ALL)) {
      var resultSet = statement.executeQuery();
      while (resultSet.next()) {
        cats.add(buildCat(resultSet));
      }
      return cats;
    } catch (SQLException e) {
      throw new CatDaoException(e);
    }
  }

  public Optional<Cat> findById(Long id) {
    try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
      preparedStatement.setLong(1, id);

      var resultSet = preparedStatement.executeQuery();
      Cat cat = null;
      if (resultSet.next()) {
        cat = buildCat(resultSet);
      }

      return Optional.ofNullable(cat);

    } catch (SQLException e) {
      throw new CatDaoException(e);
    }
  }


  public Cat save(Cat cat) {
    Connection connection = null;
    PreparedStatement insertStatement = null;
    Statement addFriendsStatement = null;
    try {
      connection = ConnectionManager.get();
      connection.setAutoCommit(false);

      insertStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
      insertStatement.setString(1, cat.getName());
      insertStatement.setObject(2, cat.getBirthDate());
      insertStatement.setString(3, cat.getBreed());
      insertStatement.setString(4, cat.getColor());
      insertStatement.setLong(5, cat.getOwner().getId());
      insertStatement.executeUpdate();

      var result = insertStatement.getGeneratedKeys();
      if (result.next()) {
        cat.setId(result.getLong(1));
      }

      if (cat.getFriends() != null) {
        addFriendsStatement = connection.createStatement();
        for (Long catFriendId : cat.getFriends()) {
          addFriendsStatement.addBatch(
              String.format(ADD_FRIENDSHIP_SQL, cat.getId(), catFriendId));
        }
        addFriendsStatement.executeBatch();
      }

      connection.commit();

      return cat;

    } catch (Exception e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException ex) {
          throw new CatDaoException(ex);
        }
        try {
          connection.setAutoCommit(true);
        } catch (SQLException ex) {
          throw new CatDaoException(ex);
        }
      }
      throw new CatDaoException(e);
    } finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          throw new CatDaoException(e);
        }
      }
      if (insertStatement != null) {
        try {
          insertStatement.close();
        } catch (SQLException e) {
          throw new CatDaoException(e);
        }
      }
      if (addFriendsStatement != null) {
        try {
          addFriendsStatement.close();
        } catch (SQLException e) {
          throw new CatDaoException(e);
        }
      }
    }
  }

  public void update(Cat cat) {
    Connection connection = null;
    PreparedStatement statementUpdate = null;
    Statement statementUpdateFriends = null;
    try {
      connection = ConnectionManager.get();
      statementUpdate = connection.prepareStatement(UPDATE_SQL);
      statementUpdateFriends = connection.createStatement();
      connection.setAutoCommit(false);
      statementUpdate.setString(1, cat.getName());
      statementUpdate.setObject(2, cat.getBirthDate());
      statementUpdate.setString(3, cat.getBreed());
      statementUpdate.setString(4, cat.getColor());
      statementUpdate.setLong(5, cat.getOwner().getId());
      statementUpdate.setLong(6, cat.getId());
      statementUpdate.executeUpdate();

      statementUpdateFriends.addBatch(
          String.format(DELETE_FRIENDSHIP_SQL, cat.getId(), cat.getId()));
      for (Long catFriendId : cat.getFriends()) {
        statementUpdateFriends.addBatch(
            String.format(ADD_FRIENDSHIP_SQL, cat.getId(), catFriendId));
      }
      statementUpdateFriends.executeBatch();

      connection.commit();
    } catch (SQLException e) {
        if (connection != null) {
          try {
            connection.rollback();
          } catch (SQLException ex) {
            throw new CatDaoException(ex);
          }
          try {
            connection.setAutoCommit(true);
          } catch (SQLException ex) {
            throw new CatDaoException(ex);
          }
        }
    }
    finally {
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException e) {
          throw new CatDaoException(e);
        }
      }
      if (statementUpdate != null) {
        try {
          statementUpdate.close();
        } catch (SQLException e) {
          throw new CatDaoException(e);
        }
      }
      if (statementUpdateFriends != null) {
        try {
          statementUpdateFriends.close();
        } catch (SQLException e) {
          throw new CatDaoException(e);
        }
      }
    }
  }

  public boolean delete(Long id) {
    try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
      preparedStatement.setLong(1, id);
      return preparedStatement.execute();

    } catch (SQLException e) {
      throw new CatDaoException(e);
    }
  }

}
