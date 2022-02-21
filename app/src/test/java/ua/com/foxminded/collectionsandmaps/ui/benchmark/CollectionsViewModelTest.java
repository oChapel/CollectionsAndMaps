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

    @Test
    public void testInit() {
        viewModel.init();

        verify(toastObserver).onChanged(0);
        verify(itemListObserver).onChanged(benchmark.generateCollectionItems(false));
        verifyNoMore();
    }

    @Test
    public void testCalculateTime() {
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> ImmediateThinScheduler.INSTANCE);
        viewModel.calculateTime(size);

        verify(toastObserver).onChanged(0);
        verify(errorObserver).onChanged(null);
        verify(toastObserver).onChanged(R.string.startingCalc);
        verify(toastObserver).onChanged(R.string.endingCalc);
        verify(itemListObserver).onChanged(anyList());
        verifyNoMore();

        RxJavaPlugins.reset();
    }

    @Test
    public void testStopCalculateTime() {
        viewModel.calculateTime(size);

        verify(toastObserver).onChanged(0);
        verify(toastObserver).onChanged(R.string.startingCalc);
        viewModel.calculateTime(size);
        verify(toastObserver).onChanged(R.string.stopCalc);
        verify(errorObserver, times(2)).onChanged(null);
        verify(itemListObserver, times(2)).onChanged(anyList());
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
