package fr.hibernate.search.test;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.junit.BeforeClass;
import org.junit.Test;

import fr.hibernate.search.model.Article;
import fr.hibernate.search.model.Bill;
import fr.hibernate.search.model.BillDetail;
import fr.hibernate.search.model.Client;

public class HibernateSearchTest extends TestBase {

	private static EntityManagerFactory emf;
	private static EntityManager em;
	
	private static int CLIENTS_TOTAL = 10;
	private static int ARTICLES_TOTAL = 10;
	private static int BILLS_TOTAL = 3;
	private static int BILLDETAILS_TOTAL_PER_BILL = 15;

	@BeforeClass
	public static void setUpEmf() {
		emf = Persistence.createEntityManagerFactory("HibernateSearchPU");
		em = emf.createEntityManager();
	}

	@Test
	public void testInsert() {
		this.insert();
		em.close();
	}
	
	@Test
	public void testUpdates() {
		this.updateOneClient();
		this.updateOneArticle();
		em.close();
	}
	
	@Test
	public void testBulkInsert() throws InterruptedException {
		Session session = (Session) em.getDelegate();
		FullTextSession fts = Search.getFullTextSession(session);
		fts.createIndexer(BillDetail.class)
			.batchSizeToLoadObjects(500)
			.cacheMode(CacheMode.NORMAL)
			.threadsToLoadObjects(2)
			.startAndWait();
	}

	private void insert() {
		inTransaction(em, tx -> {
			
			insertArticles();
			insertClients();

			List<Article> articles = this.getArticles(em);
			List<Client> clients = this.getClients(em);
			
			for (int i = 0; i < BILLS_TOTAL; i++) {
				Bill b = new Bill();
				b.setDate(new Date());
				b.setClient(clients.get(new Random().nextInt(clients.size() - 1)));
				em.persist(b);
				for (int j = 0; j < BILLDETAILS_TOTAL_PER_BILL; j++) {
					BillDetail d = this.createBillDetail(clients, articles, b, j);
					em.persist(d);
				}
			}
		});
	}
	
	private void updateOneClient() {
		inTransaction(em, tx -> {
			List<BillDetail> billDetails = this.getBillDetail(em);
			BillDetail billDetail = billDetails.get(0);
			Client client = billDetail.getBill().getClient();
			client.name = "NEW " + client.name;
			em.merge(client);
		});
	}

	private void updateOneArticle() {
		inTransaction(em, tx -> {
			List<BillDetail> billDetails = this.getBillDetail(em);
			BillDetail billDetail = billDetails.get(0);
			billDetail.getArticle().designation = "NEW " + billDetail.getArticle().designation;
			em.merge(billDetail.getArticle());
		});
	}

	private BillDetail createBillDetail(List<Client> clients, List<Article> articles, Bill b, int number) {
		BillDetail d = new BillDetail(b.getId(), number);
		d.setArticle(articles.get(new Random().nextInt(ARTICLES_TOTAL)));
		d.setPrize(new Random().nextDouble() * 100);
		d.setQuantity(new Random().nextDouble() * 10);
		b.getBillDetails().add(d);
		d.bill = b;
		return d;
	}

	private void insertArticles() {
		for (int i = 0; i < ARTICLES_TOTAL; i++) {
			Article a = new Article();
			a.designation = "Article " + i;
			em.persist(a);
		}
	}

	private void insertClients() {
		for (int i = 0; i < CLIENTS_TOTAL; i++) {
			Client c = new Client();
			c.name = "Client " + i;
			em.persist(c);
		}
	}

	private List<BillDetail> getBillDetail(EntityManager em) {
		Session hibernateSession = (Session) em.getDelegate();
		FullTextSession fullTextSession = Search.getFullTextSession(hibernateSession);
		org.hibernate.Query query = fullTextSession.createQuery("SELECT e FROM BillDetail e");
		return (List<BillDetail>) query.list();
	}

	private List<Client> getClients(EntityManager em) {
		Query query = em.createQuery("SELECT e FROM Client e");
		return (List<Client>) query.getResultList();
	}

	private List<Article> getArticles(EntityManager em) {
		Query query = em.createQuery("SELECT e FROM Article e");
		return (List<Article>) query.getResultList();
	}
}