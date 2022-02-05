package stock;// The Stock program is following the MVC design template and this is our controller object.
// The main functionality for buying and selling the stocks are in this controller object.

import java.util.*;
import java.util.Queue;

public class Controller {
	public Controller() {
		LinkedList<Stock> stockList = new LinkedList<Stock>();
		Scanner input = new Scanner(System.in);
		boolean stockExists = false;
		int quantity;
		double price = 0;
		int selection;
		do {
			System.out.println("Press 1 for buying, 2 for selling, 3 to exit: ");
			selection = input.nextInt();
			if(selection != 1 && selection != 2)
				break;
			System.out.println("Enter Stock name: ");
			String sName = input.next();
			System.out.println("Enter Quantity: ");
			quantity = input.nextInt();
			if (selection == 1) {
				System.out.println("At what price would you like to purchase: ");
				price = input.nextDouble();
				buyStock(stockList, sName, quantity, price);
			}
			else {
				while (isPresent(stockList, sName) == false) {
					System.out.println("Stock not present please insert name again: ");
					sName = input.nextLine();
				}
				while (isQuantity(stockList, sName, quantity) == false) {
					System.out.println("You are trying to sell more than you own, please re enter amount: ");
					quantity = input.nextInt();
				}
				System.out.println("At what price would you like to sell: ");
				price = input.nextDouble();
				System.out.println("Press 1 for LIFO Selling, 2 for FIFO Selling: ");
				int sellingSelection = input.nextInt();
				if (sellingSelection == 1) sellLIFO(stockList, sName, quantity);
				else sellFIFO(stockList, sName, quantity);
			}
		}
		while (selection != 3);
	}

			
	public static void buyStock(LinkedList<Stock> list, String name, int quantity, double price) {
		Stock temp = new Stock(name,quantity,price);
		list.push(temp);
		System.out.printf("You bought %d shares of %s stock at $%.2f per share %n", quantity, name, price);
	}
	
	public static void sellLIFO(LinkedList<Stock> list,String name, int numToSell) {
		Stack<Stock> stack = new Stack<>();
		double total = 0;
		double profit = 0;
		int count = 0;
		int i = list.size()-1;
		while(count < numToSell || i >= 0) {
			Stock stock = list.get(i);
			if (stock.getName().equals(name) && stock.getQuantity() <= numToSell) {
				stack.push(stock);
				count += stock.getQuantity();
			}
			i--;
		}
		while(count > 0){
			Stock stock = stack.peek();
			total+= stock.getPrice()*stock.getQuantity();
			count-= stock.getQuantity();
			stack.pop();
		}
		profit = total - total/numToSell;
		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, name, total/numToSell);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	}
	
	public static void sellFIFO(LinkedList<Stock> list, String name, int numToSell) {
		Queue<Stock> q = new LinkedList<>();
		double total = 0;
		double profit = 0;
		int count = 0;
		int i = 0;
		while(count < numToSell || i < list.size()) {
			Stock stock = list.get(i);
			if (stock.getName().equals(name) && stock.getQuantity() <= numToSell) {
				q.add(stock);
				count += stock.getQuantity();
			}
			i++;
		}
		while(count > 0){
			Stock stock = q.peek();
			total+= stock.getPrice()*stock.getQuantity();
			count-= stock.getQuantity();
			q.remove();
		}
		profit = total - total/numToSell;
		System.out.printf("You sold %d shares of %s stock at %.2f per share %n", numToSell, list.element().getName(), total/numToSell);
	    System.out.printf("You made $%.2f on the sale %n", profit);
	}
	public static boolean isPresent(LinkedList<Stock> list, String name){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getName().equals(name))
				return true;
		}
		return false;
	}
	public static boolean isQuantity(LinkedList<Stock> list, String name, int quantity){
		int stockNo = 0;
		for(int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(name))
				stockNo += list.get(i).getQuantity();
		}
		if(stockNo <= quantity)
			return false;
		return true;
	}
}
