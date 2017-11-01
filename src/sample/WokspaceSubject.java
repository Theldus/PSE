package sample;

import sample.nodes.NodeBox;
import sample.util.Coordinates;

import javax.xml.soap.Node;

public interface WokspaceSubject {

    public void addObserver( NodeBoxObserver  nodeBoxObserver );
    public void removeObserver(NodeBoxObserver nodeBoxObserver );
    public void notifyAllObservers( Coordinates coordinates );
    public void setChange();

}
