import java.util.Random;

class Philosopher extends Thread {
	private Chopstick left, right;
	private Random random;
	public int count;
	
	public Philosopher(Chopstick left, Chopstick right, int count) {
		this.left = left;
		this.right = right;
		this.count = count;
		random = new Random();
	}
	
	public void run() {
		try {
			while (true) {
				Thread.sleep(random.nextInt(1000));
				System.out.println("thinking");
				synchronized (left) {
					synchronized (right) {
						Thread.sleep(random.nextInt(1000));
						System.out.println("eating");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

class Chopstick {}


public class DiningPhilosophers {

	public static void main(String[] args) {
		Chopstick left = new Chopstick();
		Chopstick right = new Chopstick();
		for (int i = 0; i < 5; i++) {
			Philosopher p = new Philosopher(left, right, i);
			p.start();
		}
	}

}
