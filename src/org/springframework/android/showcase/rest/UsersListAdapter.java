package org.springframework.android.showcase.rest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.springframework.android.showcase.R;

import java.util.List;

/**
 * Created by Ramida on 2016-08-07.
 */
public class UsersListAdapter extends BaseAdapter {

    private List<Users> users;
    private final LayoutInflater layoutInflater;

    public UsersListAdapter(Context context, List<Users> users) {
        this.users = users;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.users != null ? users.size() : 0;
    }

    public Users getItem(int position) {
        return this.users.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.twitter_timeline_list_item, parent, false);
        }

        Users user1 = getItem(position);
        if (user1 != null) {
            TextView t = (TextView) convertView.findViewById(R.id.tweet_from_user);
            t.setText(user1.getUsername());

            t = (TextView) convertView.findViewById(R.id.tweet_text);
            t.setText(user1.getRole());
        }

        return convertView;
    }

}
