package VegetableShop.VegShop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface vegRepository extends JpaRepository<Veg, Long>{

	@Query("SELECT v  FROM Veg v WHERE CONCAT(v.name,' ',v.price) LIKE %?1% ")
	public List<Veg> search(String keyword);
	
	@Query("SELECT v FROM Veg v WHERE name = ?1")
	public Veg searchName(String name);
	
}
