

import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.SortedBag;
import org.apache.commons.collections4.bag.*;
import org.junit.jupiter.api.Test;

import java.util.*;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class BagUnitTest {

    @Test
    public void whenAdded_thenCountIsKept(){
        List<Integer> list = Arrays.asList(1,2,3,3,3,1,4);
        Bag<Integer> bag = new HashBag<>(list);
        assertThat(bag.getCount(1), equalTo(2));
    }

    @Test
    public void givenBag_whenBagAddAPILikeCollectionAPI_thenFalse(){
        Collection<Integer> collection = new ArrayList<>();

        // Collection contract defines that add() should return false
        assertThat(collection.add(9), is(true));

        //Even when element is already in the collection
        collection.add(1);
        assertThat(collection.add(1), is(true));

        Bag<Integer> bag = new HashBag<>();

        assertThat(bag.add(9), is(true));
        bag.add(1);
        assertThat(bag.add(1), is(not(true)));

    }

    @Test
    public void givenDecoratorBag_whenBagAddAPILikeCollectionAPI_thenTrue(){
        Bag<Integer> bag = CollectionBag.collectionBag(new HashBag<>());
        bag.add(1);
        assertThat(bag.add(1), is((true)));
    }

    @Test
    public void givenAdd_whenCountOfElementsDefined_thenCountAreAdded(){
        Bag<Integer> bag = new HashBag<>();
        bag.add(1,5);
        assertThat(bag.getCount(1), equalTo(5));
    }

    @Test
    public void givenMultipleCopies_whenRemove_allAreRemoved(){
        Bag<Integer> bag = new HashBag<>(Arrays.asList(new Integer [] {1,2,3,3,3,3,1,4}));
        bag.remove(3,1);
        assertThat(bag.getCount(3),equalTo(2));

        bag.remove(1);
        assertThat(bag.getCount(1), equalTo(0));
    }


    @Test
    public void givenTree_whenDuplicateElementsAdded_thenSort(){
        TreeBag<Integer> bag = new TreeBag<>(Arrays.asList(new Integer[]{7,5,1,7,2,3,3,3,1,4,7}));
        assertThat(bag.first(), equalTo(1));
        assertThat(bag.getCount(bag.first()), equalTo(2));
        assertThat(bag.last(), equalTo(7));
        assertThat(bag.getCount(bag.last()), equalTo(3));
    }

    @Test
    public void givenDecoratedTree_whenTreeAddAPILikeCollectionAPI_thenTrue(){
        SortedBag<Integer> bag = CollectionSortedBag.collectionSortedBag(new TreeBag<>());
        bag.add(1);
        assertThat(bag.add(1), is((true)));
    }


    @Test
    public void givenSortedBag_whenDuplicateElementsAdded_thenSort(){
        SynchronizedSortedBag<Integer> bag = SynchronizedSortedBag
                .synchronizedSortedBag(new TreeBag<>(Arrays.asList(new Integer[] {7,5,1,7,2,3,3,3,1,4,7})));

        assertThat(bag.first(), equalTo(1));
        assertThat(bag.getCount(bag.first()), equalTo(2));
        assertThat(bag.last(), equalTo(7));
        assertThat(bag.getCount(bag.last()), equalTo(3));
    }


}
