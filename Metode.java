package main;

public class Metode {
	public static int[] podeli(int old_data[], int polinom[]) {
		int ostatak[], i;
		int data[] = new int[old_data.length + polinom.length];
		for(int k=0;k<data.length;k++) {
			data[k]=0;
		}
		System.arraycopy(old_data, 0, data, 0, old_data.length);

		ostatak = new int[polinom.length];
		// Na pocetku cemo ostatak postaviti na data bite (prvih polinom.length elemenata)
		System.arraycopy(data, 0, ostatak, 0, polinom.length);

		//for petlja duzine poslate poruke
		for (i = 0; i < old_data.length; i++) {//Ispis ispod se odnosi na prvi bit ostatka!
			System.out.println((i + 1) + ".) Prvi bit podatka je : " + ostatak[0]);
			System.out.print("Ostatak : ");
			if (ostatak[0] == 1) {
				//XOR sa polinomom
				for (int j = 1; j < polinom.length; j++) {
					ostatak[j - 1] = ostatak[j]^polinom[j];
					System.out.print(ostatak[j - 1]);
				}
			} else {
				// Kada je najlevlji broj 0, xorujemo ostatak sa nulama
				// Ovde smo mogli samo da prepisemo u levo elemente niza,
				//ali zbog doslednosti algoritma je i ovo XOR-ovano nulama
				for (int j = 1; j < polinom.length; j++) {
					ostatak[j - 1] = ostatak[j]^0;
					System.out.print(ostatak[j - 1]);
				}
			}
			//dodamo novi poslednji element od data
			ostatak[polinom.length - 1] = data[i + polinom.length];
			System.out.println(ostatak[polinom.length - 1]);
		}
		return ostatak;
	}

	public static void primi(int data[], int polinom[]) {
		//Primili smo poruku, i sada, racunajuci da primalac ima isti polinom kao posiljalac, proveravamo da li je podatak
		//dobro primljen
		int ostatak[] = podeli(data, polinom);

		for (int i = 0; i < ostatak.length; i++) {
			if (ostatak[i] != 0) {
				System.out.println("Doslo je do greske pri prijemu podatka...");
				return;
			}
		}
		System.out.println("Podatak je primljen bez greske.");
	}

}
