/**
 * 
 */
package bean;

/**
 * The NavigationBean class is responsible for storing the state of the two
 * panel stacks which display dynamic content. 
 *
 */
public class NavigationBean {
	private PageContentBean selectedPanel;
	
	
	public String toMenu(){
		return "toMenu";
	}
	public String toCapture(){
		return "toCapture";
	}
	public String toBusqueda(){
		return "toBusqueda";
	}
	
	public String toCatalogos(){
		return "toCatalogos";
	}
	
	public String toImport(){
		return "toImport";
	}
    // selected page content bean.
    

    /**
     * Gets the currently selected content panel.
     *
     * @return currently selected content panel.
     */
    public PageContentBean getSelectedPanel() {
        return selectedPanel;
    }

    /**
     * Sets the selected panel to the specified panel.
     *
     * @param selectedPanel a none null page content bean.
     */
    public void setSelectedPanel(PageContentBean selectedPanel) {
        if (selectedPanel != null && selectedPanel.isPageContent()) {
            this.selectedPanel = selectedPanel;
        }
    }

}
