package nao.cycledev.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ToDo_List extends Activity {
	
	private static final int ADD_TODO_ITEM_REQUEST = 0;
	
	ToDoListLoader toDoListLoader;
	ToDoListAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_to_do__list);
		
		// For load/save data into text file
		toDoListLoader = new ToDoListLoader(getApplicationContext());

		mAdapter = new ToDoListAdapter(getApplicationContext());
		
		ListView lvToDoList = (ListView)findViewById(R.id.lvToDoList);
		lvToDoList.setFooterDividersEnabled(true);
		
		LayoutInflater li = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		TextView footerView = (TextView)li.inflate(R.layout.todo_footer, null, false);
		lvToDoList.addFooterView(footerView);

		footerView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent addToDoItem = new Intent(ToDo_List.this, AddToDoItem.class);				
				startActivityForResult(addToDoItem, ADD_TODO_ITEM_REQUEST);			}
		});
		
		lvToDoList.setAdapter(mAdapter);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if ((requestCode == ADD_TODO_ITEM_REQUEST) && (resultCode == RESULT_OK)) {	 
			ToDoItem item = new ToDoItem(data); 
			mAdapter.add(item);			 
		}	
	}

	@Override
	public void onResume() {
		super.onResume();
		if (mAdapter.getCount() == 0)
			toDoListLoader.loadToDoList(mAdapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		toDoListLoader.saveToDoList(mAdapter);
	}

}
