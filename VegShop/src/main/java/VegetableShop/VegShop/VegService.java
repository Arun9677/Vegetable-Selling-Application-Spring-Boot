package VegetableShop.VegShop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class VegService {
	
	@Autowired
	vegRepository vegRepository;
	
	@Autowired
	employRepository empRepository;
	
	@Autowired
	adminRepository admRepository;
	
	@Autowired
	tempBillRepository tempRepository;
	
	@Autowired
	finalRepository finRepository;
	
	public List<Veg> listAll()
	{
		return vegRepository.findAll();
	}
	
	public List<Employ> employListAll()
	{
		return empRepository.findAll();
	}
	
	public List<Admin> adminListAll()
	{
		return admRepository.findAll();
	}
	
	public List<Bill> tempListAll()
	{
		return tempRepository.findAll();
	}
	
	public List<FinalBill> finalListAll()
	{
		return finRepository.findAll();
	}
	
	public void saveVeg(Veg veg)
	{
		vegRepository.save(veg);
	}
	
	public void saveEmploy(Employ employ)
	{
		empRepository.save(employ);
	}
	
	public void saveAdmin(Admin admin)
	{
		admRepository.save(admin);
	}
	
	public void saveTemp(Bill bill)
	{
		tempRepository.save(bill);
	}
	
	public void saveFinal(FinalBill finalBill)
	{
		finRepository.save(finalBill);
	}
	
	public void removeVeg(long id)
	{
		vegRepository.deleteById(id);
	}
	
	public void deleteEmploy(long id)
	{
		empRepository.deleteById(id);
	}
	
	public void removeTemp(long id)
	{
		tempRepository.deleteById(id);
	}
	
	public void deleteTemp()
	{
		tempRepository.deleteAll();
	}
	
	public Veg getVeg(long id)
	{
		return vegRepository.findById(id).get();
	}
	
	public Page<Veg> buyVeg(int page, String sortField, String ord)
	{
		Pageable pageable = PageRequest.of(page-1,5, ord.equals("asc")?Sort.by(sortField).ascending():Sort.by(sortField).descending());
		System.out.println(vegRepository.findAll(pageable));
		return vegRepository.findAll(pageable);
	}
	
	public List<Veg> searchVeg(String keyword)
	{
		if(keyword!=null)
		{
			return vegRepository.search(keyword);
		}
		return vegRepository.findAll();
	}
	
	public Veg searchName(String name)
	{
		if(name!=null)
		{
			return vegRepository.searchName(name);
		}
		return null;
	}
	
	public Admin logInAdmin(String userId, String Password)
	{
		return admRepository.LogInAdmin(userId, Password);
	}

//	public Admin logInAdmin(String userId, String Password)
//	{
//		List<Admin> list = admRepository.findAll();
//		for(Admin a: list)
//		{
//			if(a.getUserId().equals(userId))
//			{
//				if(a.getPassword().equals(Password))
//				{
//					return a;
//				}
//			}
//		}
//		return null;
//	}

	public Employ logInEmploy(String userId, String Password)
	{
		return empRepository.LogInEmploy(userId, Password);
	}
	
}
