package de.mbrero.see.persistance.dto;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * Table representation for article entity.
 * @author massimiliano.brero@studium.fernuni-hagen.de
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
	
    /**
     * 
     * @return int
     */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @param int id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param String name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getArticleId() {
		return articleId;
	}
	
	/**
	 * 
	 * @param String aid
	 */
	public void setArticleId(String aid) {
		this.articleId = aid;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String getSource() {
		return source;
	}
	
	/**
	 * 
	 * @param String source
	 */
	public void setSource(String source) {
		this.source = source;
	}  
	  
}
