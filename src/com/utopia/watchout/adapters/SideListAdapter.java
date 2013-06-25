
package com.utopia.watchout.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.utopia.watchout.R;
import com.utopia.watchout.adapters.SideListAdapter.SideItem;
import com.utopia.watchout.fragments.WOFragment.WOFragType;
import com.utopia.watchout.model.STTable.Province;
import com.utopia.watchout.utils.Utils;

import java.util.List;

public class SideListAdapter extends ArrayAdapter<SideItem> {

    public SideListAdapter(Context context, List<SideItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.menu_item, null);
        }

        SideItem sideItem = getItem(position);
        if (sideItem.isHeader()) {
            Utils.findViewByIdInTag(convertView, R.id.side_header).setVisibility(View.VISIBLE);
            Utils.findViewByIdInTag(convertView, R.id.side_content).setVisibility(View.GONE);
            TextView headerText = (TextView) Utils.findViewByIdInTag(convertView,
                    R.id.side_header_title);
            headerText.setText(sideItem.getTitle());
        } else {
            Utils.findViewByIdInTag(convertView, R.id.side_header).setVisibility(View.GONE);
            Utils.findViewByIdInTag(convertView, R.id.side_content).setVisibility(View.VISIBLE);
            TextView contentText = (TextView) Utils.findViewByIdInTag(convertView,
                    R.id.side_content_text);
            contentText.setText(sideItem.getTitle());
        }

        if (sideItem.isSelected()) {
            Utils.findViewByIdInTag(convertView, R.id.side_content_marker).setVisibility(
                    View.VISIBLE);
            Utils.findViewByIdInTag(convertView, R.id.side_content_selected).setVisibility(
                    View.VISIBLE);
        } else {
            Utils.findViewByIdInTag(convertView, R.id.side_content_marker).setVisibility(
                    View.INVISIBLE);
            Utils.findViewByIdInTag(convertView, R.id.side_content_selected).setVisibility(
                    View.INVISIBLE);
        }

        if (sideItem.isMargin()) {
            Utils.findViewByIdInTag(convertView, R.id.side_margin).setVisibility(View.VISIBLE);
            Utils.findViewByIdInTag(convertView, R.id.side_item).setVisibility(View.GONE);
        } else {
            Utils.findViewByIdInTag(convertView, R.id.side_margin).setVisibility(View.GONE);
            Utils.findViewByIdInTag(convertView, R.id.side_item).setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public static class SideItem {

        boolean mIsMargin = false;
        boolean mIsSelected = false;
        boolean mIsHeader = false;
        String mTitle = null;
        WOFragType mType = WOFragType.NULL;

        public SideItem(String title, boolean isHeader) {
            mTitle = title;
            mIsHeader = isHeader;
        }

        public SideItem(String title, WOFragType type) {
            mTitle = title;
            mType = type;
        }

        public SideItem(Province prov) {
            mTitle = prov.getName();
            mType = WOFragType.STATISTICS;
        }

        public SideItem(boolean isMargin) {
            mIsMargin = true;
        }

        public SideItem setSelected(boolean isSelected) {
            mIsSelected = isSelected;
            return this;
        }

        public boolean isMargin() {
            return mIsMargin;
        }

        public boolean isSelected() {
            return mIsSelected;
        }

        public String getTitle() {
            return mTitle;
        }

        public boolean isHeader() {
            return mIsHeader;
        }

        public WOFragType getFragType() {
            return mType;
        }
    }
}
