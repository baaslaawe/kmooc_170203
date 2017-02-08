package com.nile.kmooc.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.google.inject.Inject;
import com.nile.kmooc.discussion.DiscussionRequestFields;
import com.nile.kmooc.discussion.DiscussionService;
import com.nile.kmooc.discussion.DiscussionThread;
import com.nile.kmooc.http.CallTrigger;
import com.nile.kmooc.http.ErrorHandlingCallback;
import com.nile.kmooc.model.Page;
import com.nile.kmooc.util.ResourceUtil;
import com.nile.kmooc.util.SoftKeyboardUtil;
import com.nile.kmooc.view.adapters.InfiniteScrollUtils;
import com.nile.kmooc.view.common.MessageType;
import com.nile.kmooc.view.common.TaskProcessCallback;
import com.nile.kmooc.view.common.TaskProgressCallback;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class CourseDiscussionPostsSearchFragment extends CourseDiscussionPostsBaseFragment {

    @Inject
    private DiscussionService discussionService;

    @InjectExtra(value = Router.EXTRA_SEARCH_QUERY, optional = true)
    private String searchQuery;

    @InjectView(com.nile.kmooc.R.id.discussion_topics_searchview)
    private SearchView discussionTopicsSearchView;

    private Call<Page<DiscussionThread>> searchThreadListCall;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(com.nile.kmooc.R.layout.fragment_discussion_search_posts, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        discussionTopicsSearchView.setQuery(searchQuery, false);
        discussionTopicsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query == null || query.trim().isEmpty())
                    return false;
                SoftKeyboardUtil.hide(getActivity());
                searchQuery = query;
                nextPage = 1;
                controller.reset();
                discussionPostsListView.setVisibility(View.INVISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void loadNextPage(@NonNull final InfiniteScrollUtils.PageLoadCallback<DiscussionThread> callback) {
        ((TaskProcessCallback) getActivity()).onMessage(MessageType.EMPTY, "");
        if (searchThreadListCall != null) {
            searchThreadListCall.cancel();
        }
        final List<String> requestedFields = Collections.singletonList(
                DiscussionRequestFields.PROFILE_IMAGE.getQueryParamValue());
        searchThreadListCall = discussionService.searchThreadList(
                courseData.getCourse().getId(), searchQuery, nextPage, requestedFields);
        final Activity activity = getActivity();
        final TaskProgressCallback progressCallback = activity instanceof TaskProgressCallback ?
                (TaskProgressCallback) activity : null;
        searchThreadListCall.enqueue(new ErrorHandlingCallback<Page<DiscussionThread>>(
                activity, CallTrigger.LOADING_UNCACHED,
                // Initially we need to show the spinner at the center of the screen. After that,
                // the ListView will start showing a footer-based loading indicator.
                nextPage > 1 || callback.isRefreshingSilently() ? null : progressCallback) {
            @Override
            protected void onResponse(@NonNull final Page<DiscussionThread> threadsPage) {
                ++nextPage;
                callback.onPageLoaded(threadsPage);
                Activity activity = getActivity();
                if (activity instanceof TaskProcessCallback) {
                    if (discussionPostsAdapter.getCount() == 0) {
                        String escapedTitle = TextUtils.htmlEncode(searchQuery);
                        String resultsText = ResourceUtil.getFormattedString(
                                getContext().getResources(),
                                com.nile.kmooc.R.string.forum_no_results_for_search_query,
                                "search_query",
                                escapedTitle
                        ).toString();
                        // CharSequence styledResults = Html.fromHtml(resultsText);
                        ((TaskProcessCallback) activity).onMessage(MessageType.ERROR, resultsText);
                    } else {
                        ((TaskProcessCallback) activity).onMessage(MessageType.EMPTY, "");
                        discussionPostsListView.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Page<DiscussionThread>> call, @NonNull Throwable error) {
                // Don't display any error message if we're doing a silent
                // refresh, as that would be confusing to the user.
                if (!callback.isRefreshingSilently()) {
                    super.onFailure(call, error);
                }
                callback.onError();
                nextPage = 1;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        SoftKeyboardUtil.clearViewFocus(discussionTopicsSearchView);
    }
}
