package model;

import java.util.Comparator;

public class ComparatorByName implements Comparator<User>{

		@Override
		public int compare(User u1, User u2) {
			String name1=u1.getName();
			String name2=u2.getName();
			String lastN1=u1.getLastName();
			String lastN2=u2.getLastName();
			int comp=0;
			if(name1.compareTo(name2)<0) {
				comp=-1;
			}else if(name1.compareTo(name2)>0){
				comp=1;
			}else {
				if(lastN1.compareTo(lastN2)<0) {
					comp=-1;
				}else if(lastN1.compareTo(lastN2)>0) {
					comp=1;
				}
			}
		return comp;
		}
}
