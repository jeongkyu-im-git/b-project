package com.test.bookproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookProjectApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(BookProjectApplication.class, args);
		
		//BookProjectApplication app = new BookProjectApplication();
		//app.testH2DB();
	}
	
/**
	private void testH2DB() {
		
		EntityManager manager = factory.createEntityManager();
		
		try {
			manager.getTransaction().begin();
			manager.persist(new LoginEntity("aaa","pass11"));
			manager.persist(new LoginEntity("bbb","pass22"));
			manager.persist(new LoginEntity("ccc","pass33"));
			manager.getTransaction().commit();
			
			Query query = manager.createQuery("select i from LoginEntity i");
			List<LoginEntity> list = query.getResultList();
			int count = 0;
			for ( LoginEntity p : list ) {
				System.out.print("No," + ++count + " ");
				System.out.println(p);
			}
			System.out.println(" -- total : " + list.size() + " login data.");
			
			manager.clear();
			
			Query query2 = manager.createQuery("select * from LoginEntity where id = 'bbb'");
//			query2.setParameter(1, "bbb");
			List<LoginEntity> list2 = query2.getResultList();
			for ( LoginEntity p : list ) {
				System.out.println("getDate ," + p );
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			manager.close();
		}
		
		
	}
**/

}
