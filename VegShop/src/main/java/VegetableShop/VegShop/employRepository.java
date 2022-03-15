package VegetableShop.VegShop;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface employRepository extends JpaRepository<Employ, Long>
{
	
	@Query("SELECT e FROM Employ e WHERE e.userId = ?1 and e.password = ?2 ")
	public Employ LogInEmploy(String userId, String Password);
	
}
