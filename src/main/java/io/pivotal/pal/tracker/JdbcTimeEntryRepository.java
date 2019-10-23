package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository{

    DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository (DataSource dataSource){
        this.dataSource = dataSource;
        jdbcTemplate = new JdbcTemplate(this.dataSource);
    }
    @Override
    public TimeEntry create(TimeEntry any) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement ps = con.prepareStatement("insert into time_entries(project_id, user_id, date, hours) values (?, ?, ?, ?)");
//                ps.setLong(1, any.getProjectId());
//                ps.setLong(2, any.getUserId());
//                ps.setDate(3, new Date(any.getDate().toEpochDay()));
//                ps.setInt(4, any.getHours());
//                return ps;
//            }
//        } , generatedKeyHolder);
        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("insert into time_entries(project_id, user_id, date, hours) values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                ps.setLong(1, any.getProjectId());
                ps.setLong(2, any.getUserId());
                ps.setDate(3, Date.valueOf(any.getDate()));
                ps.setInt(4, any.getHours());
                return ps;
        }, generatedKeyHolder);


        TimeEntry newRow = new TimeEntry(generatedKeyHolder.getKey().longValue(), any);

        return newRow;
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        List<TimeEntry> timeEntries = jdbcTemplate.query(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("select * from time_entries where id = ?");
                ps.setLong(1, timeEntryId);
                return ps;
            }
        }, new TimeEntryRowMapper());
        TimeEntry result = null;
        if (timeEntries != null && timeEntries.size() == 1) {
            result = timeEntries.get(0);
        }
        return result;
    }

    @Override
    public List<TimeEntry> list() {
        return jdbcTemplate.query("select * from time_entries", new TimeEntryRowMapper());
    }

    @Override
    public TimeEntry update(long timeEntryId, TimeEntry any) {
        int rowsUpdated = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("update time_entries set project_id = ?, user_id =?, date =?, hours =? where id = ?");
                ps.setLong(1, any.getProjectId());
                ps.setLong(2, any.getUserId());

                ps.setDate(3, Date.valueOf(any.getDate()));
                ps.setInt(4, any.getHours());
                ps.setLong(5, timeEntryId);
                return ps;
            }
        });

        TimeEntry updatedTimeEntry = null;
        if (rowsUpdated == 1) {
            updatedTimeEntry = new TimeEntry(timeEntryId, any);
        }
        return updatedTimeEntry;
    }

    @Override
    public void delete(long timeEntryId) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("delete from time_entries where id = ?");
                ps.setLong(1, timeEntryId);
                return ps;
            }
        });
    }
}
