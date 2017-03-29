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
 * Created by PK on 2017-03-25.
 */

public class DrawersListAdapter extends BaseAdapter {



    private List<Drawer_Container> drawers1;
    private final LayoutInflater layoutInflater;

    public DrawersListAdapter(Context context, List<Drawer_Container> drawers2) {
        this.drawers1 = drawers2;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return this.drawers1 != null ? drawers1.size() : 0;
    }

    public Drawer_Container getItem(int position) {
        return this.drawers1.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.twitter_timeline_list_item, parent, false);
        }

        Drawer_Container user1 = getItem(position);
        if (user1 != null) {
            TextView t = (TextView) convertView.findViewById(R.id.tweet_from_user);
            t.setText(user1.getShortDescription());

            t = (TextView) convertView.findViewById(R.id.tweet_text);
            t.setText("Last modification " + user1.getLastAction());
        }

        return convertView;
    }

}
