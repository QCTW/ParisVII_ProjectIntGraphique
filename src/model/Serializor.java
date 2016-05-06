package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import view.ViewableGraph;

public class Serializor
{
	private File targetFile;
	private String strMsg = "";

	public Serializor()
	{
	}

	public Serializor(File fileToSerialize)
	{
		targetFile = fileToSerialize;
	}

	public void setFile(File fileToSerialize)
	{
		targetFile = fileToSerialize;
	}

	public String getMessage()
	{
		return strMsg;
	}

	public void serialize(Object o)
	{
		ObjectOutputStream out = null;
		try
		{
			FileOutputStream fileOut = new FileOutputStream(targetFile);
			out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
			strMsg = "Your graph is saved to:" + targetFile.getAbsolutePath();
		} catch (IOException i)
		{
			i.printStackTrace();
		} finally
		{
			if (out != null)
				try
				{
					out.close();
				} catch (IOException e)
				{
					System.out.println("Unable to close file:" + targetFile.getAbsolutePath());
				}
		}
	}

	public ViewableGraph deserialize()
	{
		ViewableGraph mp = null;
		ObjectInputStream in = null;
		try
		{
			FileInputStream fileIn = new FileInputStream(targetFile);
			in = new ObjectInputStream(fileIn);
			mp = (ViewableGraph) in.readObject();
			strMsg = "Your graph is opened from:" + targetFile.getAbsolutePath();
		} catch (IOException i)
		{
			i.printStackTrace();
		} catch (ClassNotFoundException c)
		{
			System.out.println("Unable to find class:" + ViewableGraph.class.getSimpleName());
		} finally
		{
			if (in != null)
				try
				{
					in.close();
				} catch (IOException e)
				{
					System.out.println("Unable to close file:" + targetFile.getAbsolutePath());
				}
		}
		return mp;
	}

}
