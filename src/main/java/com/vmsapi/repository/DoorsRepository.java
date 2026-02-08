package com.vmsapi.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Repository
public class DoorsRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Object[]> findAllDoorsRaw() {
        String sql = "SELECT d.house_total, d.incharge, d.comments, d.door_no, d.visited, d.house_order, d.status, COALESCE(v.avail_count,0) as available_count " +
                     "FROM doors d LEFT JOIN (SELECT door_no, COUNT(*) FILTER (WHERE status = 'Available') AS avail_count FROM voters_new GROUP BY door_no) v ON v.door_no = d.door_no " +
                     "ORDER BY d.house_order ASC, d.door_no ASC";
        Query q = em.createNativeQuery(sql);
        //noinspection unchecked
        return (List<Object[]>) q.getResultList();
    }

    public List<Object[]> findDoorsRawByIncharge(String incharge) {
        String sql = "SELECT d.house_total, d.incharge, d.comments, d.door_no, d.visited, d.house_order, d.status, COALESCE(v.avail_count,0) as available_count " +
                     "FROM doors d LEFT JOIN (SELECT door_no, COUNT(*) FILTER (WHERE status = 'Available') AS avail_count FROM voters_new GROUP BY door_no) v ON v.door_no = d.door_no " +
                     "WHERE d.incharge = :incharge ORDER BY d.house_order ASC, d.door_no ASC";
        Query q = em.createNativeQuery(sql);
        q.setParameter("incharge", incharge);
        //noinspection unchecked
        return (List<Object[]>) q.getResultList();
    }

    // Update incharge for a door_no; returns number of rows updated
    public int updateInchargeByDoorNo(String doorNo, String incharge) {
        String sql = "UPDATE doors SET incharge = :incharge WHERE door_no = :doorNo";
        Query q = em.createNativeQuery(sql);
        q.setParameter("incharge", incharge);
        q.setParameter("doorNo", doorNo);
        return q.executeUpdate();
    }

    // Upsert incharge for a door_no (fallback to insert if update affected 0 rows)
    public int upsertInchargeByDoorNo(String doorNo, String incharge) {
        int updated = updateInchargeByDoorNo(doorNo, incharge);
        if (updated > 0) return updated;

        String insertSql = "INSERT INTO doors (door_no, incharge) VALUES (:doorNo, :incharge)";
        Query insertQ = em.createNativeQuery(insertSql);
        insertQ.setParameter("doorNo", doorNo);
        insertQ.setParameter("incharge", incharge);
        return insertQ.executeUpdate();
    }

    // Update status
    public int updateStatusByDoorNo(String doorNo, String status) {
        String sql = "UPDATE doors SET status = :status WHERE door_no = :doorNo";
        Query q = em.createNativeQuery(sql);
        q.setParameter("status", status);
        q.setParameter("doorNo", doorNo);
        return q.executeUpdate();
    }

    // Upsert status (update, else insert)
    public int upsertStatusByDoorNo(String doorNo, String status) {
        int updated = updateStatusByDoorNo(doorNo, status);
        if (updated > 0) return updated;
        String insertSql = "INSERT INTO doors (door_no, status) VALUES (:doorNo, :status)";
        Query insertQ = em.createNativeQuery(insertSql);
        insertQ.setParameter("doorNo", doorNo);
        insertQ.setParameter("status", status);
        return insertQ.executeUpdate();
    }

    // Update visited
    public int updateVisitedByDoorNo(String doorNo, String visited) {
        String sql = "UPDATE doors SET visited = :visited WHERE door_no = :doorNo";
        Query q = em.createNativeQuery(sql);
        q.setParameter("visited", visited);
        q.setParameter("doorNo", doorNo);
        return q.executeUpdate();
    }

    // Update comments
    public int updateCommentsByDoorNo(String doorNo, String comments) {
        String sql = "UPDATE doors SET comments = :comments WHERE door_no = :doorNo";
        Query q = em.createNativeQuery(sql);
        q.setParameter("comments", comments);
        q.setParameter("doorNo", doorNo);
        return q.executeUpdate();
    }

    // Update visited and comments together
    public int updateVisitedAndCommentsByDoorNo(String doorNo, String visited, String comments) {
        String sql = "UPDATE doors SET visited = :visited, comments = :comments WHERE door_no = :doorNo";
        Query q = em.createNativeQuery(sql);
        q.setParameter("visited", visited);
        q.setParameter("comments", comments);
        q.setParameter("doorNo", doorNo);
        return q.executeUpdate();
    }
}
