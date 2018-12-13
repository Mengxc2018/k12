package cn.k12soft.servo.module.holidaysWeek.repository;

import cn.k12soft.servo.module.holidaysWeek.domain.HolidaysWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface HolidaysWeekRepository extends JpaRepository<HolidaysWeek, Long>, JpaSpecificationExecutor<HolidaysWeek>{

    @Query(value = "SELECT id, date, name, is_gone"
            + " FROM holidays_week"
            + " WHERE"
            + " DATE_FORMAT(date, '%Y') = :year", nativeQuery = true)
    List<HolidaysWeek> queryYear(@Param("year") Integer year);

    @Query(value = "SELECT id, date, name, is_gone"
            + " FROM holidays_week"
            + " WHERE"
            + " DATE_FORMAT(NOW(), '%Y') = DATE_FORMAT(date, '%Y')"
            + " AND DATE_FORMAT(date, '%m') = :month", nativeQuery = true)
    List<HolidaysWeek> queryMonth(@Param("month") Integer month);

    @Query(value = "SELECT id, date, name, is_gone"
            + " FROM holidays_week"
            + " WHERE"
            + " DATE_FORMAT(date, '%Y') = :year"
            + " AND DATE_FORMAT(date, '%m') = :month", nativeQuery = true)
    List<HolidaysWeek> queryByYearAndMonth(@Param("year") Integer year,
                                           @Param("month") Integer month);

    @Query(value = "SELECT id, date, name, is_gone"
            + " FROM holidays_week"
            + " WHERE"
            + " DATE_FORMAT(date, '%Y') = :year"
            + " AND DATE_FORMAT(date, '%m') = :month"
            + " AND is_gone = 1", nativeQuery = true)
    List<HolidaysWeek> queryHolidaysByYearAndMonth(@Param("year") Integer year,
                                                   @Param("month") Integer month);

    @Modifying
    @Query(value = "DELETE FROM holidays_week"
            + " WHERE"
            + " DATE_FORMAT(date, '%Y') = :year"
            + " AND DATE_FORMAT(date, '%m') = :month", nativeQuery = true)
    void deleteByYearAndMonth(@Param("year") Integer year,
                              @Param("month") Integer month);

    @Modifying
    @Query(value = "DELETE FROM holidays_week"
            + " WHERE"
            + " DATE_FORMAT(date, '%Y') = :year", nativeQuery = true)
    void deleteYear(@Param("year") Integer year);

    @Modifying
    @Query(value = "DELETE FROM holidays_week"
            + " WHERE"
            + " DATE_FORMAT(NOW(), '%Y') = DATE_FORMAT(date, '%Y')"
            + " AND DATE_FORMAT(date, '%m') = :month", nativeQuery = true)
    void deleteMonth(@Param("month") Integer month);

    @Query(value = "SELECT COUNT(*) FROM holidays_week"
            + " WHERE "
            + " DATE_FORMAT(date, '%Y') = :year"
            + " AND DATE_FORMAT(date, '%m') = :month"
            + " AND is_gone = :yes", nativeQuery = true)
    Integer queryWorkSataAndSun(@Param("year") Integer year,
                                @Param("month") Integer month,
                                @Param("yes") int yes);

    @Query(value = "SELECT COUNT(*) FROM holidays_week"
            + " WHERE "
            + " DATE_FORMAT(date, '%Y') = :year"
            + " AND DATE_FORMAT(date, '%m') = :month"
            + " AND is_gone = 0", nativeQuery = true)
    Integer queryWeekendToWorkByYearAndMonth(@Param("year") Integer year,
                                             @Param("month") Integer month);


    @Query(value = "SELECT * FROM holidays_week"
            + " WHERE DATE_FORMAT(date, '%Y') = :year", nativeQuery = true)
    List<HolidaysWeek> findByYear(@Param("year") Integer year);

    @Query(value = "select * from holidays_week"
            + " WHERE Date_Format(date, '%Y-%m-%d') = Date_Format(:instant, '%Y-%m-%d')", nativeQuery = true)
    HolidaysWeek findByDate(@Param("instant") Instant instant);
}
