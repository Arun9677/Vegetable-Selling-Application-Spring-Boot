package VegetableShop.VegShop;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {
	
	@Autowired
	VegService service;
	
	@RequestMapping("/")
	public String index()
	{
		return "index";
	}
	
	@RequestMapping("/vegList")
	public String viewVeg(Model model)
	{
		List<Veg> vegList = service.listAll();
		model.addAttribute("vegList", vegList);
		return "vegList";
	}
	
	
	@RequestMapping("/newVeg")
	public String addVeg(Model model)
	{
		Veg veg = new Veg();
		model.addAttribute("veg", veg);
		return "addVeg";
	}
	
	@RequestMapping("/newEmploy")
	public String addEmploy(Model model)
	{
		Employ employ = new Employ();
		model.addAttribute("employ", employ);
		return "addEmploy";
	}
	
	@RequestMapping("/newAdmin")
	public String addAdmin(Model model)
	{
		Admin admin = new Admin();
		model.addAttribute("admin", admin);
		return "addAdmin";
	}
	
	@RequestMapping(value="/addVeg", method = RequestMethod.POST)
	public String saveVeg(@ModelAttribute("veg") Veg veg)
	{
		service.saveVeg(veg);
		return "redirect:/empHome";
	}
	
	@RequestMapping(value="/addEmploy", method = RequestMethod.POST)
	public String saveEmploy(@ModelAttribute("employ") Employ employ)
	{
		service.saveEmploy(employ);
		return "redirect:/EmployLogIn";
	}
	
	@RequestMapping(value="/addAdmin", method = RequestMethod.POST)
	public String saveAdmin(@ModelAttribute("admin") Admin admin)
	{
		service.saveAdmin(admin);
		return "redirect:/AdminLogIn";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView EditVeg(@PathVariable(name="id") long id)
	{
		ModelAndView view = new ModelAndView("editVeg");
		Veg veg = service.getVeg(id);
		view.addObject("veg", veg);
		return view;
	}
	
	@RequestMapping(value="/editVeg", method = RequestMethod.POST)
	public String editVeg(@ModelAttribute("veg") Veg veg)
	{
		service.saveVeg(veg);
		return "redirect:/admHome";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteVeg(@PathVariable(name="id") long id)
	{
		service.deleteEmploy(id);
		return "redirect:/list";
	}
	
	@RequestMapping("/admHome")
	public String admHome()
	{
		return "AdminHome";
	}
	
	@RequestMapping("/empHome")
	public String empHome()
	{
		return "EmployHome";
	}
	
	@RequestMapping("/sortnamePage")
	public String sortNamePage(Model model)
	{
		return listItems(model,1,"name","asc");
	}
	
	@RequestMapping("/page/{page}")
	public String listItems(Model model,@PathVariable(name="page") int page, @Param("sortField") String sortField, @Param("ord") String ord)
	{ 
		Page<Veg> pg = service.buyVeg(page, sortField, ord);
		System.out.println("pg = "+pg);
		List<Veg> list = pg.getContent();
		System.out.println("list = "+list);
		model.addAttribute("currentpage", page);
		model.addAttribute("tp",pg.getTotalPages());
		model.addAttribute("ti",pg.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("ord",ord);
		model.addAttribute("sd",ord.equals("des")?"asc":"des");
		model.addAttribute("pgnlist", list);
		return "list";		
	}
	
	@RequestMapping("/search")
	public String searchVeg(Model model, @Param("keyword") String keyword)
	{
		List<Veg> vegList = service.searchVeg(keyword);
		model.addAttribute("vegList", vegList);
		model.addAttribute("keyword", keyword);
		return "search";
	}
	
	@RequestMapping("/AdminLogIn")
	public String LogInAdmin(Model model)
	{
		Admin admin = new Admin();
		model.addAttribute("admin", admin);
		return "AdminLogIn";
	}
	
	@RequestMapping("/EmployLogIn")
	public String LogInEmploy(Model model)
	{
		Employ employ = new Employ();
		model.addAttribute("employ", employ);
		return "EmployLogIn";
	}
	
	@RequestMapping("/AdminSignIn")
	public String SignInAdmin(@ModelAttribute("admin") Admin admin)
	{
		Admin verify = service.logInAdmin(admin.getUserId(), admin.getPassword());
		if(verify == null)
		{
			return "AdminLogIn";
		}
		return "AdminHome";
	}
	
	@RequestMapping("/EmploySignIn")
	public String SignInEmploy(@ModelAttribute("employ") Employ employ)
	{
		Employ verify = service.logInEmploy(employ.getUserId(), employ.getPassword());
		if(verify == null)
		{
			return "EmployLogIn";
		}
		return "EmployHome";
	}
	
	@RequestMapping("/list")
	public String vegList()
	{
		return "list";
	}
	
	@RequestMapping("/billing")
	public String billing(Model model, @Param("vegBill") ArrayList<Bill> vegBill, @Param("optVeg") ArrayList<Veg> optVeg, @Param("name") String name, @Param("quantity") String quantity, @Param("total") String total, @Param("CName") String CName, @Param("CNum") String CNum)
	{		
		
		double tempTotal, newTotal, tempQty, tempPrice, newQty, newPrice, oldQty, oldPrice; 
		long billId;
		String billName;
		double billQuantity, billPrice;
		
		if(name != null)
		{
			Veg oldVeg = service.searchName(name);
			if(quantity != null)
			{
				tempQty = Double.parseDouble(quantity);
				if(oldVeg.getQuantity() > tempQty)
				{
					billId = oldVeg.getId();
					billName = oldVeg.getName();
					tempPrice = oldVeg.getPrice();
					oldQty = oldVeg.getQuantity();
					oldQty = oldQty - tempQty;
					oldVeg.setQuantity(oldQty);
					service.saveVeg(oldVeg);
										
					Bill bill = new Bill();
					bill.setId(billId);
					bill.setName(billName);
					billPrice = tempPrice * tempQty;
					bill.setPrice(billPrice);
					bill.setQuantity(tempQty);
					service.saveTemp(bill);
								
				}
			}
		}
		
		optVeg = (ArrayList<Veg>) service.listAll();
		vegBill = (ArrayList<Bill>) service.tempListAll();
		
		model.addAttribute("optVeg", optVeg);
		model.addAttribute("vegBill", vegBill);
		model.addAttribute("CName", CName);
		model.addAttribute("CNum", CNum);
		
		return "billing";
		
	}
	
	@RequestMapping("/bill")
	public String bill(Model model, @Param("CName") String CName, @Param("CNum") String CNum)
	{
		List<Bill> bills = service.tempListAll();
		double BillAmount = 0;
		for(Bill b: bills)
		{
			BillAmount = BillAmount + b.getPrice();
		}
		
		FinalBill finalBill = new FinalBill();
		finalBill.setDate(new Date());
		finalBill.setCustomername(CName);
		finalBill.setCustomernumber(CNum);
		finalBill.setAmount(BillAmount);
		
		model.addAttribute("vegBill", bills);
		model.addAttribute("CName", CName);
		model.addAttribute("CNum", CNum);
		
		service.saveFinal(finalBill);
		service.deleteTemp();
		
		return "bill";
	}
	
}
