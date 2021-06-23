package edu.tamu.aser.tests.examples.atmoerror;

public class Customer implements Runnable{

	private BankAccount account;
	  private int cash;

	  public Customer(int cash, BankAccount account) {
	      this.cash = cash;
	      this.account = account;
	  }

	  public void cost(int n) {
	      cash -= n;
	      account.add(n);
	  }

	  @Override
	  public void run() {
	      while(cash > 0) {  //ֱ����Ǯ�ù�
	          cost(1);
	      }
	      System.out.println("total: " + account.getTotal());   //��ӡ�������˻����ܼƽ��
	      if(account.getTotal() != 10)
	    	  throw new RuntimeException();
	  }

}
