package dao;

import static java.util.stream.Collectors.*;

import dto.*;
import entity.*;
import exception.*;
import java.sql.*;
import java.time.*;
import java.util.*;
import util.*;

public class OwnerDao implements Dao<Owner, Long> {

  private static OwnerDao instance = new OwnerDao();

  private static final String INSERT_SQL = """
      INSERT INTO kotiki.owners (name, birth_date) values (?, ?)
      """;

  private static final String UPDATE_SQL = """
      Update kotiki.owners SET name = ?, birth_date = ? Where id = ?
      """;

  private static final String DELETE_SQL = """
      Delete From kotiki.owners Where id = ?
      """;

  private static final String FIND_ALL_SQL = """
      Select id, name, birth_date
      From kotiki.owners
      """;

  private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + "Where id = ?";

  private OwnerDao() {
  }

  public static OwnerDao getInstance() {
    return instance;
  }

  private Owner buildOwner(ResultSet resultSet) throws SQLException {
    return Owner.builder().id(resultSet.getLong("id")).name(resultSet.getString("name"))
        .birthDate((LocalDate) resultSet.getObject("birth_date")).build();
  }

  @Override
  public List<Owner> findAll() {
    List<Owner> owners = new ArrayList<>();
    try (var connection = ConnectionManager.get();
        var statement = connection.prepareStatement(FIND_ALL_SQL)) {
      var resultSet = statement.executeQuery();
      while (resultSet.next()) {
        owners.add(buildOwner(resultSet));
      }
      return owners;
    } catch (SQLException e) {
      throw new CatDaoException(e);
    }
  }

  public List<Owner> findAll(OwnerFilter filter) {
    List<Object> parameters = new ArrayList<>();
    List<String> whereSql = new ArrayList<>();
    if (filter.name() != null) {
      whereSql.add("name = ?");
      parameters.add(filter.name());
    }
    parameters.add(filter.limit());
    parameters.add(filter.offset());
    var where = whereSql.stream()
        .collect(joining("And", "Where", "Limit ? Offset ? "));

    var sql = FIND_ALL_SQL + where;

    try (var connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

      for (int i = 0; i < parameters.size(); i++) {
        preparedStatement.setObject(i + 1, parameters.get(i));
      }
      System.out.println(preparedStatement);
      var resultSet = preparedStatement.executeQuery();
      List<Owner> owners = new ArrayList<>();
      while (resultSet.next()) {
        owners.add(buildOwner(resultSet));
      }
      return owners;
    } catch (SQLException e) {
      throw new OwnerDaoException(e);
    }
  }


  public Optional<Owner> findById(Long id) {
    try (var connection = ConnectionManager.get();
        var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
      preparedStatement.setLong(1, id);
      var result = preparedStatement.executeQuery();
      Owner owner = null;
      if (result.next()) {
        owner = buildOwner(result);
      }
      return Optional.ofNullable(owner);
    } catch (SQLException e) {
      throw new OwnerDaoException(e);
    }
  }

  public Owner save(Owner owner) {
    try (var connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL,
            Statement.RETURN_GENERATED_KEYS)) {
      preparedStatement.setString(1, owner.getName());
      preparedStatement.setObject(2, owner.getBirthDate());
      preparedStatement.executeUpdate();

      var result = preparedStatement.getGeneratedKeys();
      if (result.next()) {
        owner.setId(result.getLong(1));
      }
      return owner;
    } catch (SQLException e) {
      throw new OwnerDaoException(e);
    }
  }

  public void update(Owner owner) {
    try (var connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {

      preparedStatement.setString(1, owner.getName());
      preparedStatement.setObject(2, owner.getBirthDate());
      preparedStatement.setLong(3, owner.getId());
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new OwnerDaoException(e);
    }
  }

  public boolean delete(Long id) {
    try (var connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SQL)) {

      preparedStatement.setLong(1, id);
      return preparedStatement.execute();
    } catch (SQLException e) {
      throw new OwnerDaoException(e);
    }
  }

}
