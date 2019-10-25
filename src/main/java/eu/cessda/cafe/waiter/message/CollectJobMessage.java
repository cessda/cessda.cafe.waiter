package eu.cessda.cafe.waiter.message;

/*
 * Java class to store /collect-jobs Response message 
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CollectJobMessage {
	private int x;
	private int y;
		
	public CollectJobMessage() {
		super();
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public String toString() {
        return x + " job(s) collected" + ", still waiting for " + y + " job(s).";
	}
}
