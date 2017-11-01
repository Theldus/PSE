package sample.nodes;

import sample.Workspace.Workspace;

public class NodeBoxFactory {

    private static NodeBoxFactory ourInstance = new NodeBoxFactory();

    public static NodeBoxFactory getInstance() {
        return ourInstance;
    }

    private NodeBoxFactory() {
    }

    public void createGaussianFilter(Workspace workspace){
         new GaussianFilter(workspace).install();
    }

    public void createHistogram(Workspace workspace){
         new Histogram(workspace).install();
    }

}
