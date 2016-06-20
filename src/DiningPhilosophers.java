import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Philosopher extends Thread {
	private boolean eating;
	private Philosopher left;
	private Philosopher right;
	private ReentrantLock table;
	private Condition condition;
	private Random random;
	private static int cnt = 1;
	private int id;
	
	public Philosopher(ReentrantLock table) {
		eating = false;
		this.table = table;
		condition = table.newCondition();
		random = new Random();
		this.id = cnt;
		++cnt;
	}
	
	public Philosopher(ReentrantLock table, Philosopher left, Philosopher right) {
		eating = false;
		this.table = table;
		condition = table.newCondition();
		random = new Random();
		this.id = cnt;
		++cnt;
		this.left = left;
		this.right = right;
	}
	
	public void setLeft(Philosopher left) {
		this.left = left;
	}
	
	public void setRight(Philosopher right) {
		this.right = right;
	}
	
	public void run() {
		try {
			while (true) {
				think();
				eat();
			}
		} catch (InterruptedException e) {
			// TODO: handle exception
		}
	}
	
	private void think() throws InterruptedException {
		table.lock();
		try {
			eating = false;
			System.out.println("ph " + id + " is thinking");
			left.condition.signal();
			right.condition.signal();
		} finally {
			table.unlock();
		}
		Thread.sleep(random.nextInt(1000));
	}
	
	private void eat() throws InterruptedException {
		table.lock();
		try {
			while (left.eating || right.eating) {
				condition.await();
			}
			eating =  true;
			System.out.println("ph " + id + " is eatnig");
		} finally {
			table.unlock();
		}
		Thread.sleep(random.nextInt(1000));
	}
}

class Chopstick {}


public class DiningPhilosophers {

	public static void main(String[] args) {
		ReentrantLock  table = new ReentrantLock();
		Philosopher p1 = new Philosopher(table);
		Philosopher p2 = new Philosopher(table);
		Philosopher p3 = new Philosopher(table);
		Philosopher p4 = new Philosopher(table);
		Philosopher p5 = new Philosopher(table);
		p1.setLeft(p5);p1.setRight(p2);
		p2.setLeft(p1);p2.setRight(p3);
		p3.setLeft(p2);p3.setRight(p4);
		p4.setLeft(p3);p4.setRight(p5);
		p5.setLeft(p4);p5.setRight(p1);
		p1.start();
		p2.start();
		p3.start();
		p4.start();
		p5.start();
	}

}
