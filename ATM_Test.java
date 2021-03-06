import java.io.*;
import java.util.*;


public class ATM_Test {
	
	static boolean readFile(String path) {
		LineNumberReader reader = null;
		try {
			reader = new LineNumberReader(new FileReader(path));
			while(true) {
				String str = reader.readLine();
				if(str == null) {
					break;
				}
				String[] token = str.split(" ");
				
				accMap.put(token[0], new Account(token[0], token[1], token[2], Integer.parseInt(token[3])));
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println("파일을 찾을 수 없습니다.");
		} catch (IOException ioe) {
			System.out.println("파일을 읽을 수 없습니다.");
		} finally {
			try {
				reader.close();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	static boolean writeFile(String path) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(path);
			for(String a : accMap.keySet()) {
				writer.write(a+" "+accMap.get(a).name+" "+accMap.get(a).pw+" "+accMap.get(a).nowMoney+"\n");
			}
		} catch (IOException e) {
			System.out.println("파일을 출력할 수 없습니다.");
		} finally {
			try {
				writer.close();
				return true;
			} catch (Exception e2) {
				return false;
			}
		}
	}
	
	static HashMap<String, Account> accMap = new HashMap<String, Account>();
	
	public static void main(String[] args) {
		String name, acc, pw, sendA;
		int nowMoney, money;
		
		Scanner sc = new Scanner(System.in);
	
		int but1 = 0;
		readFile("accList.txt");
		
		System.out.println("ATM 프로그램을 실행합니다.");
		while(but1!=4) {
			int but2 = 0;
			System.out.println("1.계좌 등록 2.모든 계좌 조회 3.로그인 4.종료");
			but1 = sc.nextInt();
			
			switch(but1) {
			case 1: 
				System.out.println("예금주 : "); name = sc.next();
				System.out.println("계좌번호 : "); acc = sc.next();
				
				Account accCode = accMap.get(acc);
				if(accCode==null) {
					System.out.println("비밀번호 : "); pw = sc.next();
					System.out.println("입금할 금액 : "); money = sc.nextInt();
					accMap.put(acc, new Account(acc, name, pw, money));
					System.out.println(name+"님의 계좌가 생성되었습니다.");
					
				}else {
					System.out.println("이미 등록된 계좌번호입니다.");
				}
				writeFile("accList.txt");
				break;
			case 2:
				for(String user : accMap.keySet()) {
					System.out.println(accMap.get(user).name +" / "+user);
				}
				break;
			case 3:
				if(accMap.isEmpty()) {
					System.out.println("등록된 계좌가 없습니다.");
				}else {
					System.out.println("계좌번호 : "); acc=sc.next();
					accCode = accMap.get(acc);
					if(accCode!=null) {
						System.out.println(accCode.name+"님 반갑습니다.");
						System.out.println("다음 화면으로 이동합니다.");
						
						while(but2!=5) {
							System.out.println("1.조회 2.입금 3.출금 4.송금 5.로그아웃");
							but2=sc.nextInt();
							
							switch(but2) {
							
							case 1:
								System.out.println("비밀번호 : "); pw = sc.next();
								accCode.showMoney(pw);
								break;
							
							case 2:
								System.out.println("입금액 : "); money = sc.nextInt();
								accCode.credit(money);
								System.out.println(money+"원 입급되었습니다.");
								writeFile("accList.txt");
								break;
							
							case 3:
								System.out.println("비밀번호 : "); pw = sc.next();
								System.out.println("출금액 : "); money = sc.nextInt();
								
								if(accCode.debit(pw, money)!=0) {
									System.out.println(money+"원 출금되었습니다.");
								}
								writeFile("accList.txt");
								break;
							
							case 4: 
								System.out.println("송금할 계좌 : "); sendA = sc.next();
								System.out.println("송금액 : "); money = sc.nextInt();
								System.out.println("비밀번호 : "); pw = sc.next();
								Account sendAcc = accMap.get(sendA);
								if(sendAcc!=null) {
									accCode.send(sendAcc, pw, money);
								}else {
									System.out.println("등록되지 않은 계좌번호 입니다.");
								}
								writeFile("accList.txt");
								break;
							
							case 5: 
								System.out.println("처음 화면으로 돌아갑니다.");
								break;
							default:
								System.out.println("올바른 번호를 입력해주세요.");
								break;
							}
						}
					}else {
						System.out.println("등록되지 않은 계좌번호입니다.");
					}
				}
				break;
			case 4:
				System.out.println("프로그램을 종료합니다.");
				break;
			default:
				System.out.println("올바른 번호를 입력해주세요.");
				break;
			
			}
		}
	}
}
