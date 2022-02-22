package ua.com.foxminded.collectionsandmaps.ui.benchmark;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import io.reactivex.rxjava3.internal.schedulers.ImmediateThinScheduler;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ua.com.foxminded.collectionsandmaps.R;
import ua.com.foxminded.collectionsandmaps.TrampolineSchedulerRule;
import ua.com.foxminded.collectionsandmaps.models.benchmark.Benchmark;
import ua.com.foxminded.collectionsandmaps.models.benchmark.Items;

@RunWith(MockitoJUnitRunner.class)
public class CollectionsViewModelTest {

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Rule
    public TrampolineSchedulerRule schedulerRule = new TrampolineSchedulerRule();

    @Mock
    Benchmark benchmark;
    @Mock
    Observer<Integer> toastObserver;
    @Mock
    Observer<Integer> errorObserver;
    @Mock
    Observer<List<Items>> itemListObserver;
    private CollectionsViewModel viewModel;
    private final String size = "1000000";

    @Before
    public void setUp() {
        this.viewModel = new CollectionsViewModel(benchmark);
        viewModel.getToastStatus().observeForever(toastObserver);
        viewModel.getSizeErrorStatus().observeForever(errorObserver);
        viewModel.getItemsList().observeForever(itemListObserver);

        when(benchmark.generateCollectionItems(true)).thenReturn(generateTestList(true));
        when(benchmark.generateCollectionItems(false)).thenReturn(generateTestList(false));
    }

    @After
    public void tearDown() {
        viewModel.getToastStatus().removeObserver(toastObserver);
        viewModel.getSizeErrorStatus().removeObserver(errorObserver);
        viewModel.getItemsList().removeObserver(itemListObserver);
    }

    private void verifyNoMore() {
        verifyNoMoreInteractions(toastObserver);
        verifyNoMoreInteractions(itemListObserver);
        verifyNoMoreInteractions(errorObserver);
    }

    private List<Items> generateTestList(boolean visibilityFlag) {
        return new ArrayList<>(Collections.nCopies(10, new Items(0, 0, "N/A", visibilityFlag)));
    }

    @Test
    public void testInit() {
        viewModel.init();

        verify(toastObserver).onChanged(0);
        verify(itemListObserver).onChanged(generateTestList(false));
        verifyNoMore();
    }

    @Test
    public void testCalculateTime() {
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> ImmediateThinScheduler.INSTANCE);
        viewModel.calculateTime(size);

        verify(toastObserver).onChanged(0);
        verify(errorObserver).onChanged(null);
        verify(itemListObserver).onChanged(generateTestList(true));
        verify(toastObserver).onChanged(R.string.startingCalc);
        verify(toastObserver).onChanged(R.string.endingCalc);
        verify(itemListObserver, times(10)).onChanged(anyList());
        verifyNoMore();

        RxJavaPlugins.reset();
    }

    @Test
    public void testStopCalculateTime() {
        viewModel.calculateTime(size);

        verify(toastObserver).onChanged(0);
        verify(toastObserver).onChanged(R.string.startingCalc);
        verify(itemListObserver).onChanged(generateTestList(true));

        viewModel.calculateTime(size);

        verify(toastObserver).onChanged(R.string.stopCalc);
        verify(errorObserver, times(2)).onChanged(null);
        verify(itemListObserver).onChanged(generateTestList(false));
        verifyNoMore();
    }

    @Test
    public void testCalculateTimeError() {
        viewModel.calculateTime("abc");

        verify(toastObserver).onChanged(0);
        verify(errorObserver).onChanged(R.string.invalidInput);
        verifyNoMore();
    }
}
