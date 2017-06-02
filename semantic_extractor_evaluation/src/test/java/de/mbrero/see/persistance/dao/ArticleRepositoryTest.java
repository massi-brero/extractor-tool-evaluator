package de.mbrero.see.persistance.dao;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import de.mbrero.see.persistance.DBConnection;
import de.mbrero.see.persistance.dto.Article;



public class ArticleRepositoryTest {
	private Repository<Article> repo = null;
	private DBConnection conn = null;

	@Before
	public void setUp() throws Exception {
		URL url = getClass().getClassLoader().getResource("hibernate.cfg.xml");
		conn = new DBConnection(url);
		this.repo = new Repository<>(Article.class, conn);
	}

	@After
	public void tearDown() throws Exception {
		Session session = this.conn.getNewSession();
		Transaction t = session.beginTransaction();
	    Query query = session.createQuery("delete from Article");
	    query.executeUpdate();
	    t.commit();
	    session.close();
	}
	
	@Test
	public void testSave() {
		Article art = new Article();
		art.setArticleId("HM123");
		art.setName("Artikel");
		art.setSource("PubMed");
		
		repo.save(art);
		ArrayList<Article> items = (ArrayList<Article>) repo.getAll();

		assertEquals(1, items.size());
	}

	@Test
	public void testGet() {
		Article art = new Article();
		art.setArticleId("HM123");
		art.setName("Artikel");
		art.setSource("PubMed");
		
		repo.save(art);
		Article item = (Article) repo.get(1);

		assertEquals("Artikel", item.getName());
	}

	@Test
	public void testGetAll() {
		ArrayList<Article> items= (ArrayList<Article>) repo.getAll();

		assertEquals(0, items.size());
	}

	@Test
	public void testDeleteById() {
		Article art = new Article();
		art.setArticleId("HM123");
		art.setName("Artikel");
		art.setSource("PubMed");
		
		repo.save(art);
		repo.delete(1);
		ArrayList<Article> items = (ArrayList<Article>) repo.getAll();

		assertEquals(0, items.size());
	}
	
	@Test
	public void testDeleteByEntity() {
		Article art = new Article();
		art.setArticleId("HM123");
		art.setName("Artikel");
		art.setSource("PubMed");
		
		repo.save(new Article());
		Article item = repo.get(1);
		
		repo.delete(item);
		ArrayList<Article> items = (ArrayList<Article>) repo.getAll();

		assertEquals(0, items.size());
	}

	@Test
	public void testUpdate() {
		Article art = new Article();
		art.setArticleId("HM123");
		art.setName("Artikel");
		art.setSource("PubMed");
		
		repo.save(art);
		
		Article item = (Article) repo.get(1);
		item.setName("Foo");
		repo.update(item);

		assertEquals("Foo", item.getName());
	}

}
