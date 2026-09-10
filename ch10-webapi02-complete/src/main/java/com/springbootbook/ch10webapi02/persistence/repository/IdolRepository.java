package com.springbootbook.ch10webapi02.persistence.repository;

import com.springbootbook.ch10webapi02.persistence.entity.Idol;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IdolRepository {

    private final JdbcClient jdbcClient;

    public IdolRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<Idol> selectById(Integer id) {
        Optional<Idol> idolOptional = jdbcClient.sql("""
                        SELECT id, name, birthday, blood_type
                        FROM idol WHERE id = :id
                        """)
                .param("id", id)
                .query(new DataClassRowMapper<>(Idol.class))
                .optional();
        return idolOptional;
    }

    public List<Idol> selectByNameOrderById(String nameKeyword) {
        List<Idol> idolList = jdbcClient.sql("""
                        SELECT id, name, birthday, blood_type
                        FROM idol WHERE name LIKE :name ORDER BY id
                        """)
                .param("name", "%" + nameKeyword + "%")
                .query(new DataClassRowMapper<>(Idol.class))
                .list();
        return idolList;
    }

    public int countById(Integer id) {
        int count = jdbcClient.sql("""
                        SELECT COUNT(*) FROM idol
                        WHERE id = :id
                        """)
                .param("id", id)
                .query(Integer.class)
                .single();
        return count;
    }

    public int update(Idol idol) {
        int rows = jdbcClient.sql("""
                        UPDATE idol
                        SET name = :name, birthday = :birthday, blood_type = :blood_type
                        WHERE id = :id
                        """)
                .param("name", idol.name())
                .param("birthday", idol.birthday())
                .param("blood_type", idol.bloodType().name())
                .param("id", idol.id())
                .update();
        return rows;
    }

    public int delete(Integer id) {
        int rows = jdbcClient.sql("""
                        DELETE FROM idol WHERE id = :id
                        """)
                .param("id", id)
                .update();
        return rows;
    }

    public Idol insert(Idol idol) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcClient.sql("""
                        INSERT INTO idol(name, birthday, blood_type)
                        VALUES(:name, :birthday, :blood_type)
                        """)
                .param("name", idol.name())
                .param("birthday", idol.birthday())
                .param("blood_type", idol.bloodType().name())
                .update(keyHolder, "id");
        int newId = keyHolder.getKey().intValue();
        return new Idol(newId, idol.name(), idol.birthday(), idol.bloodType());
    }
}
