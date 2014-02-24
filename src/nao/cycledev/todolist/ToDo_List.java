package nao.cycledev.todolist;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

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
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.to_do__list, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_add_new_item:
	        	Intent addToDoItem = new Intent(ToDo_List.this, AddToDoItem.class);				
				startActivityForResult(addToDoItem, ADD_TODO_ITEM_REQUEST);	
	            return true;
	        case R.id.action_settings:
	        	//TODO settings
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
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
