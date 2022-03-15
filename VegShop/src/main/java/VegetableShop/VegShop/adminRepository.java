package VegetableShop.VegShop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface adminRepository extends JpaRepository<Admin, Long>
{
	
	@Query("SELECT a FROM Admin a WHERE a.userId = ?1 and a.password = ?2")
	public Admin LogInAdmin(String userId, String Password);
	
}
