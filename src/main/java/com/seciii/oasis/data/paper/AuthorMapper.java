package com.seciii.oasis.data.paper;

import com.seciii.oasis.po.Author;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AuthorMapper {

    void insertAuthor(Author author);

    void updateAuthor(Author author);

    void insertCidPid(@Param("cid")int cid, @Param("pid")int pid);

    void insertAidPid(@Param("aid")int aid, @Param("pid")int pid, @Param("rank")int rank);

    void insertAidIid(@Param("aid")int aid, @Param("iid")int iid);

    int selectByAidIid(@Param("aid")int aid, @Param("iid")int iid);

    List<Integer> selectAidByPid(@Param("pid")int pid);

    Author selectAuthorByAid(@Param("aid") int aid);

    List<Author> selectAuthorByName(@Param("aname") String aname);

    Author selectAuthorByExactName(@Param("aname") String aname);

    List<Author> selectAuthorByIid(@Param("iid")int iid);

}
