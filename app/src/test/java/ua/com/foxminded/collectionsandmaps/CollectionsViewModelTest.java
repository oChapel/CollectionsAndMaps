package ua.com.foxminded.collectionsandmaps;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CollectionsViewModelTest {

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Rule
    public TrampolineSchedulerRule schedulerRule = new TrampolineSchedulerRule();

    @Mock Benchmark benchmark;
    @Mock Observer<Integer> toastObserver;
    @Mock Observer<Integer> errorObserver;
    @Mock Observer<List<Items>> itemListObserver;
    private CollectionsViewModel viewModel;
    private String size;

    @Before
    public void setUp() {
        this.viewModel = new CollectionsViewModel(benchmark);
        viewModel.getToastStatus().observeForever(toastObserver);
        viewModel.getSizeErrorStatus().observeForever(errorObserver);
        viewModel.getItemsList().observeForever(itemListObserver);
        size = "1000000";
    }

    @After
    public void tearDown() {
        viewModel.getToastStatus().removeObserver(toastObserver);
        viewModel.getSizeErrorStatus().removeObserver(errorObserver);
        viewModel.getItemsList().removeObserver(itemListObserver);
    }

    public void verifyInit() {
        viewModel.init();
        verify(toastObserver).onChanged(0);
        verify(itemListObserver).onChanged(benchmark.generateCollectionItems(false));
        verifyNoMoreInteractions(toastObserver);
        verifyNoMoreInteractions(itemListObserver);
    }

    @Test
    public void testInit() {
        verifyInit();
    }

    @Test
    public void testCalculateTime() throws InterruptedException {
        verifyInit();
        viewModel.calculateTime(size);
        verify(toastObserver).onChanged(R.string.startingCalc);
        verify(itemListObserver, times(2)).onChanged(anyList());
        Thread.sleep(1000);
        verify(toastObserver).onChanged(R.string.endingCalc);
    }

    @Test
    public void testStopCalculateTime() {
        verifyInit();
        viewModel.calculateTime(size);
        verify(toastObserver).onChanged(R.string.startingCalc);
        viewModel.calculateTime(size);
        verify(toastObserver).onChanged(R.string.stopCalc);
        verify(itemListObserver, times(3)).onChanged(anyList());
    }

    @Test
    public void testCalculateTimeError() {
        viewModel.calculateTime("abc");
        verify(errorObserver).onChanged(R.string.invalidInput);
        verifyNoMoreInteractions(errorObserver);
    }
}