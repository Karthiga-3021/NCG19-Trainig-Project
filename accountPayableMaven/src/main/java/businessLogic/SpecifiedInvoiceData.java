package businessLogic;

/**
 * 
 * @author kdhanasekaran
 *This class have getter and setter method of invoice specified data
 */
public class SpecifiedInvoiceData {

	String invoiceNo = " ", invoiceDate = " ", customerPO = " ", orderNo = " ", totalInvoice = " ",
			soldAddress = " ", status = " UnApproved";

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCustomerPO() {
		return customerPO;
	}

	public void setCustomerPO(String customerPO) {
		this.customerPO = customerPO;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTotalInvoice() {
		return totalInvoice;
	}

	public void setTotalInvoice(String totalInvoice) {
		this.totalInvoice = totalInvoice;
	}

	public String getSoldAddress() {
		return soldAddress;
	}

	public void setSoldAddress(String soldAddress) {
		this.soldAddress = soldAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
