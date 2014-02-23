package nao.cycledev.todolist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Date;
import android.content.Context;
import android.content.ContextWrapper;

public class ToDoListLoader extends ContextWrapper {
	
	private static final String FILE_NAME = "ToDoListData.txt";
	
	public ToDoListLoader(Context base) {
		super(base);
	}	

	public void loadToDoList(ToDoListAdapter mAdapter) {
		BufferedReader reader = null;
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			reader = new BufferedReader(new InputStreamReader(fis));

			String title = null;
			String priority = null;
			String status = null;
			Date date = null;

			while ((title = reader.readLine()) != null) {
				priority = reader.readLine();
				status = reader.readLine();
				date = ToDoItem.FORMAT.parse(reader.readLine());
				mAdapter.add(new ToDoItem(title, ToDoItem.Priority.valueOf(priority), ToDoItem.Status.valueOf(status), date));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	public void saveToDoList(ToDoListAdapter mAdapter) {
		PrintWriter writer = null;
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			
			writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
			
			for (int idx = 0; idx < mAdapter.getCount(); idx++)
				writer.println(mAdapter.getItem(idx));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}		
	}
}
