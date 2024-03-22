package com.example.demo.cat;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;

public interface CatRepository extends ListPagingAndSortingRepository<Cat, Long>, ListCrudRepository<Cat, Long> {
    List<Cat> findByName(String name);

    @EntityGraph(attributePaths = {"vaccinationList"})
    List<Cat> findAllBy();

    List<CatNameAndAge> findCatsBy();

    @Query("""
            update Cat c set c.chipped = true where c.id = ?1
            """)
    @Modifying
    @Transactional
    int setChipped(Long id);

    @Query(value = """
            select * from cat where id > ?1 limit ?2
            """, nativeQuery = true)
    List<Cat> findCatsBy(long cursor, int pageSize);
}
