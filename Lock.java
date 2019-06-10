package lock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Lock implements Serializable
{
	private static final long serialVersionUID = 1L;
	static int usedid = 0;
	private String name = "";
	private String password = "0000";
	private boolean open = true;
	private boolean connected = false;
	private int id;
	
	public Lock(String name)
	{
		id = usedid;
		this.name = name;
		usedid++;
	}
	
	public String getHiddenPassword()
	{
		String out = "";
		for(int i=0; i<password.length(); i++)
		{
			out = out + "*";
		}
		return out;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean isOpen()
	{
		return open;
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	public String toString()
	{
		return Integer.toString(id) + " " + name;
	}
	public void reConnect() {/*TODO*/}
}

class GroupofLocks implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Lock> locks;
	
	public GroupofLocks()
	{
		locks = new ArrayList<Lock>();
	}
	
	public void addLock(Lock lock) {locks.add(lock);}
	
	public void reConectAll() {/*TODO*/}
	
	public void removeLock(int lock) { locks.remove(lock);}
	
	public ArrayList<Lock> getList() {return locks;}
	
	public void writeObject() throws Exception
	{
		File file = new File("save.txt");
		if(!file.exists())
		{
			file.createNewFile();
		}
		FileOutputStream writer = new FileOutputStream(file);
		Lock ll;
		try(ObjectOutputStream obj = new ObjectOutputStream(writer))
		{
			obj.writeInt(locks.size());
			for(int i=0; i<locks.size(); i++)
			{
				ll = locks.get(i);
				obj.writeObject(ll);
			}
		}
		catch(IOException error)
		{
			throw new Exception("Błąd w zapise pliku");
		}
	}
	
	public void readObject()throws Exception
	{
		FileInputStream reader = new FileInputStream(new File("save.txt"));
		try(ObjectInputStream obj = new ObjectInputStream(reader);)
		{
			int temp = obj.readInt();
			locks.clear();
			Lock ll;
			for(int i=0; i<temp; i++)
			{
				ll = (Lock) obj.readObject();
				locks.add(ll);
			}
		}
		catch(FileNotFoundException error)
		{
			throw new Exception("Nie znaleziono pliku ");
		}
		catch(IOException error)
		{
			throw new Exception("Błąd odczytu z pliku");
		} 
		catch (ClassNotFoundException error)
		{
			error.printStackTrace();
		}
	}
	
	public String[] toStringAr()
	{
		int i = 0;
		String [] tab = new String[locks.size()];
		for(Lock ll: locks)
		{
			tab[i] = ll.toString();
			i++;
		}
		return tab;
	}
}