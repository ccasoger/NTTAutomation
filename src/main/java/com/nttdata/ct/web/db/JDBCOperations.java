package com.nttdata.ct.web.db;

import com.nttdata.ct.web.service.util.UtilWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;

@Component
public class JDBCOperations {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SELECT = "SELECT ";
    private static final String FROM = " FROM ";

    private void queryLog(String query) {
        UtilWeb.logger(this.getClass()).log(Level.INFO, "Query >>> {0}", query);
    }

    public Integer countRow(String schemaTable) {
        var query = "SELECT COUNT(*) FROM " + schemaTable;
        queryLog(query);
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public List<Map<String, Object>> selectDataAsList(String schemaTable, String column) {
        var query = SELECT + column + FROM + schemaTable;
        queryLog(query);
        return jdbcTemplate.queryForList(query);
    }

    public List<Map<String, Object>> selectDataAsList(String schemaTable, String column, String condition) {
        var query = SELECT + column + FROM + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForList(query);
    }

    public Map<String, Object> selectDataAsMap(String schemaTable, String condition) {
        var query = "SELECT * FROM " + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForMap(query);
    }

    public Map<String, Object> selectDataAsMap(String schemaTable, String columns, String condition) {
        var query = SELECT + columns + FROM + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForMap(query);
    }

    public String selectDataAsString(String schemaTable, String columns, String condition) {
        var query = SELECT + columns + FROM + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForObject(query, String.class);
    }

    public Integer selectDataAsInteger(String schemaTable, String columns, String condition) {
        var query = SELECT + columns + FROM + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    public Boolean selectDataAsBoolean(String schemaTable, String columns, String condition) {
        var query = SELECT + columns + FROM + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForObject(query, Boolean.class);
    }

    public Double selectDataAsDouble(String schemaTable, String columns, String condition) {
        var query = SELECT + columns + FROM + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForObject(query, Double.class);
    }

    public Object selectDataAsObject(String schemaTable, String columns, String condition) {
        var query = SELECT + columns + FROM + schemaTable + " " + condition;
        queryLog(query);
        return jdbcTemplate.queryForObject(query, Object.class);
    }

    public boolean insertNewRow(String schemaTable, String insertColumns, String insertValues) {
        boolean inserted = false;
        var query = "INSERT INTO " + schemaTable + " " + insertColumns + " VALUES " + insertValues;
        queryLog(query);
        int rows = jdbcTemplate.update(query);
        if (rows > 0) {
            UtilWeb.logger(this.getClass()).info("Una nueva fila fue insertada.");
            inserted = true;
        } else {
            UtilWeb.logger(this.getClass()).info("Ocurrió un error al insertar una nueva fila.");
        }
        return inserted;
    }

    public boolean updateValue(String schemaTable, String valuesToUpdate, String condition) {
        boolean updated = false;
        var query = "UPDATE " + schemaTable + " SET " + valuesToUpdate + " " + condition;
        queryLog(query);
        int rows = jdbcTemplate.update(query);
        if (rows > 0) {
            UtilWeb.logger(this.getClass()).info("Una nueva fila fue actualizada.");
            updated = true;
        } else {
            UtilWeb.logger(this.getClass()).info("Ocurrió un error al actualizar una fila.");
        }
        return updated;
    }

    public boolean deleteData(String schemaTable, String condition) {
        boolean deleted = false;
        var query = "DELETE FROM " + schemaTable + " " + condition;
        queryLog(query);
        int rows = jdbcTemplate.update(query);
        if (rows > 0) {
            UtilWeb.logger(this.getClass()).info("Una nueva fila fue eliminada.");
            deleted = true;
        } else {
            UtilWeb.logger(this.getClass()).info("Ocurrió un error al eliminar una fila.");
        }
        return deleted;
    }

    public boolean createTable(String schema, String table, String newTableProperties) {
        var query = "CREATE TABLE " + schema + "." + table + " " + newTableProperties;
        queryLog(query);
        jdbcTemplate.execute(query);
        return isTableExist(null, schema, table, null);
    }

    public boolean isTableExist(String catalog, String schemaPattern, String table, String[] types) {
        boolean existTable = false;
        try (ResultSet tables = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()
                .getMetaData().getTables(catalog, schemaPattern, table, types);){
            existTable = tables.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existTable;
    }

}