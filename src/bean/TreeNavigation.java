/**
 * 
 */
package bean;

import com.icesoft.faces.component.tree.Tree;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Enumeration;

/**
 * The TreeNavigation class is the backing bean for the navigation
 * tree on the left hand side of the application. Each node in the tree is made
 * up of a PageContent which is responsible for the navigation action when a
 * tree node is selected.
 * 
 * When the Tree component binding takes place the tree nodes are initialized
 * and the tree is built.  Any addition to the tree navigation must be made to
 * this class.
 *
 */
public class TreeNavigation {
    
    // binding to component
    private Tree treeComponent;

    // bound to components value attribute
    private DefaultTreeModel model;

    // root node of tree, for delayed construction
    private DefaultMutableTreeNode rootTreeNode;

    // map of all navigation backing beans.
    private NavigationBean navigationBean;

    // initialization flag
    private boolean isInitiated;

    // folder icons for branch nodes
    private String themeBranchContractedIcon;
    private String themeBranchExpandedIcon;

    /**
     * Default constructor of the tree.  The root node of the tree is created at
     * this point.
     */
    public TreeNavigation() {
        // build root node so that children can be attached
        PageContentBean rootObject = new PageContentBean();
        rootObject.setMenuDisplayText("Country Fact Sheet");
        rootObject.setMenuContentTitle("Country Fact Sheet");
        rootObject.setMenuContentInclusionFile("./content/main.xhtml");
        rootObject.setTemplateName("mainPanel");
        rootObject.setNavigationSelection(navigationBean);
        rootObject.setPageContent(true);
        rootTreeNode = new DefaultMutableTreeNode(rootObject);
        rootObject.setWrapper(rootTreeNode);

        model = new DefaultTreeModel(rootTreeNode);
        
        // xp folders (default theme)
        themeBranchContractedIcon = "xmlhttp/css/xp/css-images/tree_folder_open.gif";
        themeBranchExpandedIcon = "xmlhttp/css/xp/css-images/tree_folder_close.gif";
    }
    
    /**
     * Utility method to build the entire navigation tree.
     */
    private void init() {
        // set init flag
        isInitiated = true;

        if (rootTreeNode != null) {

            // get the navigation bean from the faces context
            FacesContext facesContext = FacesContext.getCurrentInstance();
            Object navigationObject =
                    facesContext.getApplication()
                            .createValueBinding("#{navigation}")
                            .getValue(facesContext);


            if (navigationObject != null &&
                navigationObject instanceof NavigationBean) {

                // set bean callback for root
                PageContentBean branchObject =
                        (PageContentBean) rootTreeNode.getUserObject();

                // assign the initialized navigation bean, so that we can enable panel navigation
                navigationBean = (NavigationBean) navigationObject;

                // set this node as the default page to view
                navigationBean.setSelectedPanel(
                        (PageContentBean) rootTreeNode.getUserObject());
                branchObject.setNavigationSelection(navigationBean);

                /**
                 * Generate the backing bean for each tree node and put it all together
                 */

                // North America menu item
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("North America");
                branchObject.setMenuContentTitle("North America");
                branchObject.setTemplateName("northAmericaPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                DefaultMutableTreeNode branchNode = 
                        new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                rootTreeNode.add(branchNode);
                
                // North Ameica menu -> Canada
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Canada");
                branchObject.setMenuContentTitle("Canada Facts");
                branchObject.setMenuContentInclusionFile("./content/NA/canada.xhtml");
                branchObject.setTemplateName("canadaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                DefaultMutableTreeNode leafNode =
                        new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // North Ameica menu -> Mexico
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Mexico");
                branchObject.setMenuContentTitle("Mexico");
                branchObject.setMenuContentInclusionFile("./content/NA/mexico.xhtml");
                branchObject.setTemplateName("mexicoContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // North Ameica menu -> USA
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("USA");
                branchObject.setMenuContentTitle("USA");
                branchObject.setMenuContentInclusionFile("./content/NA/usa.xhtml");
                branchObject.setTemplateName("usaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // South America menu item
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("South America");
                branchObject.setMenuContentTitle("South America");
                branchObject.setTemplateName("southAmericaPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                branchNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                rootTreeNode.add(branchNode);
                
                // South Ameica menu -> Argentina
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Argentina");
                branchObject.setMenuContentTitle("Argentina");
                branchObject.setMenuContentInclusionFile("./content/SA/argentina.xhtml");
                branchObject.setTemplateName("argentinaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // South Ameica menu -> Brazil
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Brazil");
                branchObject.setMenuContentTitle("Brazil");
                branchObject.setMenuContentInclusionFile("./content/SA/brazil.xhtml");
                branchObject.setTemplateName("brazilContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // South Ameica menu -> Chile
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Chile");
                branchObject.setMenuContentTitle("Chile");
                branchObject.setMenuContentInclusionFile("./content/SA/chile.xhtml");
                branchObject.setTemplateName("chileContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Europe menu item
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Europe");
                branchObject.setMenuContentTitle("Europe");
                branchObject.setTemplateName("europePanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                branchNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                rootTreeNode.add(branchNode);
                
                // Europe menu -> England
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("England");
                branchObject.setMenuContentTitle("England");
                branchObject.setMenuContentInclusionFile("./content/Europe/england.xhtml");
                branchObject.setTemplateName("englandContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Europe menu -> France
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("France");
                branchObject.setMenuContentTitle("France");
                branchObject.setMenuContentInclusionFile("./content/Europe/france.xhtml");
                branchObject.setTemplateName("franceContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Europe menu -> Germany
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Germany");
                branchObject.setMenuContentTitle("Germany");
                branchObject.setMenuContentInclusionFile("./content/Europe/germany.xhtml");
                branchObject.setTemplateName("germanyContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Asia menu item
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Asia");
                branchObject.setMenuContentTitle("Asia");
                branchObject.setTemplateName("asiaPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                branchNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                rootTreeNode.add(branchNode);
                
                // Asia menu -> China
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("China");
                branchObject.setMenuContentTitle("China");
                branchObject.setMenuContentInclusionFile("./content/Asia/china.xhtml");
                branchObject.setTemplateName("chinaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Asia menu -> India
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("India");
                branchObject.setMenuContentTitle("India");
                branchObject.setMenuContentInclusionFile("./content/Asia/india.xhtml");
                branchObject.setTemplateName("indiaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Asia menu -> Japan
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Japan");
                branchObject.setMenuContentTitle("Japan");
                branchObject.setMenuContentInclusionFile("./content/Asia/japan.xhtml");
                branchObject.setTemplateName("japanContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Africa menu item
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Africa");
                branchObject.setMenuContentTitle("Africa");
                branchObject.setTemplateName("africaPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                branchNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                rootTreeNode.add(branchNode);
                
                // Africa menu -> Egypt
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Egypt");
                branchObject.setMenuContentTitle("Egypt");
                branchObject.setMenuContentInclusionFile("./content/Africa/egypt.xhtml");
                branchObject.setTemplateName("egyptContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Africa menu -> Morocco
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Morocco");
                branchObject.setMenuContentTitle("Morocco");
                branchObject.setMenuContentInclusionFile("./content/Africa/morocco.xhtml");
                branchObject.setTemplateName("moroccoContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Africa menu -> South Africa
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("South Africa");
                branchObject.setMenuContentTitle("South Africa");
                branchObject.setMenuContentInclusionFile("./content/Africa/southAfrica.xhtml");
                branchObject.setTemplateName("southAfricaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
                
                // Australia menu item
                branchObject = new PageContentBean();
                branchObject.setExpanded(false);
                branchObject.setMenuDisplayText("Australia");
                branchObject.setMenuContentTitle("Australia");
                branchObject.setTemplateName("australiaPanel");
                branchObject.setNavigationSelection(navigationBean);
                branchObject.setPageContent(false);
                branchNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(branchNode);
                // finally add the new custom component branch
                rootTreeNode.add(branchNode);
                
                // Australia menu -> Australia
                branchObject = new PageContentBean();
                branchObject.setMenuDisplayText("Australia");
                branchObject.setMenuContentTitle("Australia");
                branchObject.setMenuContentInclusionFile("./content/Australia/australia.xhtml");
                branchObject.setTemplateName("australiaContentPanel");
                branchObject.setNavigationSelection(navigationBean);
                leafNode = new DefaultMutableTreeNode(branchObject);
                branchObject.setWrapper(leafNode);
                branchObject.setLeaf(true);
                // finally add the new custom component branch
                branchNode.add(leafNode);
            }
        }
        
    }
    /**
     * Gets the default tree model.  This model is needed for the value
     * attribute of the tree component.
     *
     * @return default tree model used by the navigation tree
     */
    public DefaultTreeModel getModel() {
        return model;
    }

    /**
     * Sets the default tree model.
     *
     * @param model new default tree model
     */
    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }

    /**
     * Gets the tree component binding.
     *
     * @return tree component binding
     */
    public Tree getTreeComponent() {
        return treeComponent;
    }

    /**
     * Sets the tree component binding.
     *
     * @param treeComponent tree component to bind to
     */
    public void setTreeComponent(Tree treeComponent) {
        this.treeComponent = treeComponent;
        if (!isInitiated) {
            init();
        }
    }
}