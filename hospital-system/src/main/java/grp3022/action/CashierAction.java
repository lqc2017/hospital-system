/**
 * 全琛
 * 2017年5月5日
 */
package grp3022.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.github.pagehelper.PageInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import grp3022.bean.HospitalCashier;
import grp3022.bean.HospitalCashierSo;
import grp3022.service.HospitalAccountService;
import grp3022.service.HospitalCashierService;
import grp3022.util.Context;

/**
 * @author 全琛
 *
 */
@Namespace("/cashier")
public class CashierAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private HospitalAccountService hospitalAccountService;
	@Resource
	private HospitalCashierService hospitalCashierService;
	
	private HospitalCashier cashier;
	private List<HospitalCashier> cashiers;
	private HospitalCashierSo so;
	private Integer pn;
	
	@Action(value = "home", results = { @Result(name = "success", location = "/home/cashier.jsp") })
	public String home() {
		ActionContext ct = ActionContext.getContext();
		Map<String,Object> session = ct.getSession();
		
		Long cashierId = hospitalAccountService.getAIByUsername(Context.getUsername());
		hospitalAccountService.updateByUsername(Context.getUsername());
		setCashier(hospitalCashierService.getRecordById(cashierId));
		
		/*登陆判断*/
		if(session.get("CashierId")!=null){
			System.out.println("收银员:"+this.getCashier().getName()+"已登陆");
		}else {
			session.put("cashierId", cashierId);
			System.out.println("收银员:" + this.getCashier().getName() + "上线");
		}
		return "success";
	}
	
	@Action(value = "list", results = { @Result(name = "success", location = "/cashier/list.jsp") })
	public String list() {
		ActionContext ct = ActionContext.getContext();
		
		if(so==null){
			this.setSo(new HospitalCashierSo());
		}
		
		PageInfo<HospitalCashier> pageInfo = hospitalCashierService.getPageBySo(so, pn, null);
		this.setCashiers(pageInfo.getList());
		
		ct.put("so", so);
		ct.put("pageInfo", pageInfo);
		return "success";
	}

	public HospitalCashier getCashier() {
		return cashier;
	}

	public void setCashier(HospitalCashier cashier) {
		this.cashier = cashier;
	}

	public List<HospitalCashier> getCashiers() {
		return cashiers;
	}

	public void setCashiers(List<HospitalCashier> cashiers) {
		this.cashiers = cashiers;
	}

	public HospitalCashierSo getSo() {
		return so;
	}

	public void setSo(HospitalCashierSo so) {
		this.so = so;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}
}
