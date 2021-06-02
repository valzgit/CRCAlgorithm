package main;

import java.util.*;
//NAPOMENA! Zadatak je uradjen tako da se CRC dodatak dodaje sa DESNE strane poruke!
//Zbog bolje citljivosti programa, program je napravljen tako da se unosi ceo binarni broj kao 1 broj,
//a ne kao bit po bit cime je znacajno skracena maksimalna duzina poruke(zbog ogranicenosti tipova int i long),
//ali je citljivost finalnog rezultata mnogo bolja
//najniza pozicija niza predstavlja najveci stepen polinoma!
//obezbedjena je zastita od loseg unosa korisnika

public class CRC {

	public static void main(String args[]) {
		try (Scanner scan = new Scanner(System.in)) {
			int n = 0;
			// Unosi se velicina podatka,smatra se da ce korisnik da unese broj!=0
			System.out.println("Unesite velicinu podatka:");
			n = scan.nextInt();
			n=Math.abs(n);//za slucaj da je unet negativan broj,pretvoricemo ga u pozitivan,
			//a ako je jednak nuli stavlja se podrazumevana velicina na 5
			if(n==0) {System.out.println("Velicina ne sme biti 0, postavljena je na vrednost 5!");n=5;}
			int data[] = new int[n];
			boolean vrti = true;
			long podatak;
			while (vrti) {
				System.out.println("Unesite podatak u binarnom obliku:");
				podatak = scan.nextInt();
				int j = n;
				while (j != 0) {
					if (podatak % 10 != 0 && podatak % 10 != 1) {
						j = n;
						System.out.println("Uneti broj nije u binarnom obliku!");
						break;
					} else {
						j--;
						data[j] = (int) (podatak % 10);
						podatak /= 10;
					}
					if (j == 0) {
						if(podatak!=0) {
							System.out.println("Uneti broj nije dugacak "+n+" simbola!"+"\n");break;
						}else
						vrti = false;
					}
				}
			}

			//Unosenje polinoma
			System.out.println("Unesite stepen funkcije:");
			n = scan.nextInt();
			//kao i malopre cemo obrnuti ako je negativan
			n=Math.abs(n);
			n++;//ako je polinom n-tog stepena (npr x^3+x^2+1) onda se predstavlja na 4 bita (1101)
			int polinom[] = new int[n];
			vrti = true;
			while (vrti) {
				System.out.println("Unesite funkciju u binarnom obliku (velicine "+n+" bita):");
				podatak = scan.nextInt();
				int j = n;
				while (j != 0) {
					if (podatak % 10 != 0 && podatak % 10 != 1) {
						j = n;
						System.out.println("Uneti broj nije u binarnom obliku!");
						break;
					} else {
						j--;
						polinom[j] = (int) (podatak % 10);
						podatak /= 10;
					}
					if (j == 0) {
						if(podatak!=0) {
							System.out.println("Uneti broj nije dugacak "+n+" simbola!"+"\n");break;
						}else
						vrti = false;
					}
				}
			}

			// Delimo unet podatak polinomom i cuvamo ostatak
			int ostatak[] = Metode.podeli(data, polinom);
			for (int i = 0; i < ostatak.length - 1; i++) {
				System.out.print(ostatak[i]);
			}
			System.out.println("\nGenerisani CRC kod je:");

			for (int i = 0; i < data.length; i++) {
				System.out.print(data[i]);
			}
			//poslednji bit ostatka je visak! Zato ga ne stampamo.
			for (int i = 0; i < ostatak.length - 1; i++) {
				System.out.print(ostatak[i]);
			}
			System.out.println();

			//Unosimo primljeni podatak (sa vec dodatim CRC delom odozgo)
			int poslato[] = new int[data.length + ostatak.length - 1];
			vrti = true;
			while (vrti) {
				System.out.println("Unesite primljeni podatak:");
				podatak = scan.nextLong();
				int j = data.length + ostatak.length - 1;
				while (j != 0) {
					if (podatak % 10 != 0 && podatak % 10 != 1) {
						j = n;
						System.out.println("Uneti broj nije u binarnom obliku!");
						break;
					} else {
						j--;
						poslato[j] = (int) (podatak % 10);
						podatak /= 10;
					}
					if (j == 0) {
						if(podatak!=0) {
							System.out.println("Uneti broj nije dugacak "+(data.length + ostatak.length - 1)+" simbola ili kraci!"+"\n");break;
						}else
						vrti = false;
					}
				}
			}
			Metode.primi(poslato, polinom);
			//CRC nije tacan u 100% slucajeva zato sto biranje pogresnog polinoma, u kombinaciji sa
			//nastajanjem visestrukih gresaka moze da dovede do nedetektovanja greske!(jer bi deljenje odredjenim polinomom)
			//dalo ostatak 0, iako su u poruci promenjeni odredjeni bitovi u odnosu na originalnu poruku
			//Na internetu postoji lista polinoma koji su najadekvatniji za CRC u zavisnosti od duzine poruke
		}
		catch(Exception e) {
			System.out.println("Pocetna poruka je tipa int kao i polinom, a destinaciona poruka je long.\n Uneseni broj je veci od odgovarajuceg opsega!");
		}
	}
}