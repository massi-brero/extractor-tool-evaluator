package de.mbrero.see.persistance.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * Table representation for article entity.
 * @author massi.brero@gmail.com
 *
 */
@Entity
@Table(name = "article")
public class Article {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
	private int id;  
    
    @Column(name="name")
	private String name;
	
    @Column(name="article_id")
	private String  articleId;
	
    @Column(name="source")
	private String  source;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getArticleId() {
		return articleId;
	}
	
	public void setArticleId(String aid) {
		this.articleId = aid;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}  
	  
}
