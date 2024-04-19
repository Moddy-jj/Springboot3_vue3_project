package com.example.mapper;
import com.example.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;
import java.util.List;
@Mapper
public interface ArticleMapper {

    // 新增
    @Insert("insert into article(title, content,cover_img,state,category_id,create_user, create_time, update_time)"+
    "values(#{title}, #{content},#{coverImg}, #{state}, #{categoryId}, #{createUser},#{createTime}, #{updateTime})")
    void add( Article article);
    // 分页查询
    List<Article> query(Integer userId,Integer categoryId,String state);
    //查询
    @Select("select * from article where id = #{id}")
    Article findById(Integer id);
    //更新
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=now() where id=#{id}")
    void update(Article article);
    //删除
    @Delete("delete from article where id = #{id}")
    void delete(Integer id);
}
