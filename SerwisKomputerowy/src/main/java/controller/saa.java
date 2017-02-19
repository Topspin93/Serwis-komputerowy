package controller;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class saa {

	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("serwisKomputerowy");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		entityManager.close();
		entityManagerFactory.close();
	}

}
