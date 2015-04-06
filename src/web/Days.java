package web;

public class Days {
	

	private String min;
	private String max;
    private ListOfDaysModel[] list;
 
    public void setMin(String temp) {
        this.min = temp;
    }
    
    public void setax(String temp) {
        this.max = temp;
    }
    
    public void setList(ListOfDaysModel[] lst) {
        this.list = lst;
    }
 
    public String getMin() {
        return min;
    }
    
    public String getMax() {
        return max;
    }
 
    public ListOfDaysModel[] getDataset() {
        return list;
    }
}
