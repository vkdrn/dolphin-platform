package com.canoo.dolphin.collections;

import org.testng.annotations.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestListChangeEvent {

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void constructingChangeWithNegativeFrom_shouldThrowException() {
        new ListChangeEvent.Change<>(-1, 1, Collections.<String>emptyList());
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void constructingChangeWithToSmallerThanFrom_shouldThrowException() {
        new ListChangeEvent.Change<>(1, 0, Collections.<String>emptyList());
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void constructingChangeWithNullRemovedList_shouldThrowException() {
        new ListChangeEvent.Change<>(0, 1, null);
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void constructingEventWithNullSource_shouldThrowException() {
        final ListChangeEvent.Change<String> change= new ListChangeEvent.Change<>(0, 1, Collections.<String>emptyList());
        new ListChangeEvent<>(null, Collections.singletonList(change));
    }

    @Test (expectedExceptions = NullPointerException.class)
    public void constructingEventWithNullChangeList_shouldThrowException() {
        final ObservableArrayList<String> source = new ObservableArrayList<>("Hello", "World");
        new ListChangeEvent<>(source, null);
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void constructingEventWithEmptyChangeList_shouldThrowException() {
        final ObservableArrayList<String> source = new ObservableArrayList<>("Hello", "World");
        new ListChangeEvent<>(source, Collections.<ListChangeEvent.Change<String>>emptyList());
    }

    @Test
    public void newElementsButNoRemovedElements_shouldReturnIsAddedOnly() {
        final ObservableArrayList<String> source = new ObservableArrayList<>("Hello", "World");
        final ListChangeEvent<String> event = new ListChangeEvent<>(source, 0, 1, Collections.<String>emptyList());

        final ListChangeEvent.Change<String> change = event.getChanges().get(0);

        assertThat(change.isAdded(), is(true));
        assertThat(change.isRemoved(), is(false));
        assertThat(change.isReplaced(), is(false));
    }

    @Test
    public void noNewElementsButRemovedElements_shouldReturnIsRemovedOnly() {
        final ObservableArrayList<String> source = new ObservableArrayList<>("Hello", "World");
        final ListChangeEvent<String> event = new ListChangeEvent<>(source, 1, 1, Collections.singletonList("Goodbye"));

        final ListChangeEvent.Change<String> change = event.getChanges().get(0);

        assertThat(change.isAdded(), is(false));
        assertThat(change.isRemoved(), is(true));
        assertThat(change.isReplaced(), is(false));
    }

    @Test
    public void newElementsAndRemovedElements_shouldReturnIsReplacedOnly() {
        final ObservableArrayList<String> source = new ObservableArrayList<>("Hello", "World");
        final ListChangeEvent<String> event = new ListChangeEvent<>(source, 0, 1, Collections.singletonList("Goodbye"));

        final ListChangeEvent.Change<String> change = event.getChanges().get(0);

        assertThat(change.isAdded(), is(false));
        assertThat(change.isRemoved(), is(false));
        assertThat(change.isReplaced(), is(true));
    }
}
