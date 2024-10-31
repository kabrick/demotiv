package com.kabricks.ster.demotiv.lists;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kabricks.ster.demotiv.MyDBHelper;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new PersonalFragment();
            case 1:
                return new WorkFragment();
            case 2:
                return new MusicFragment();
            case 3:
                return new MoviesFragment();
            case 4:
                return new ShoppingFragment();
            case 5:
                return new WishlistFragment();
            case 6:
                return new ErrandsFragment();
            case 7:
                return new BooksFragment();
            case 8:
                return new PlacesFragment();
            case 9:
                return new TelevisionFragment();
            case 10:
                return new OtherFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 11;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                MyDBHelper db = new MyDBHelper(mContext);
                int total = db.getTotalPersonalList();
                return "Personal (" + total + ")";
            case 1:
                return "Work";
            case 2:
                return "Music";
            case 3:
                return "Movies";
            case 4:
                return "Shopping";
            case 5:
                return "Wishlist";
            case 6:
                return "Daily Errands";
            case 7:
                return "Books to Read";
            case 8:
                return "Places to visit";
            case 9:
                return "Television Shows";
            case 10:
                return "Other Lists";
        }
        return super.getPageTitle(position);
    }
}
