package ua.com.foxminded.collectionsandmaps;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static org.mockito.Mockito.verify;

public class CollectionsViewModelTest {

    @Rule
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    @Mock Benchmark benchmark;
    @Mock Observer<Integer> toastObserver;
    @Mock Observer<Integer> errorObserver;
    @Mock Observer<List<Items>> itemListObserver;
    private CollectionsViewModel viewModel;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.viewModel = new CollectionsViewModel(benchmark);
        viewModel.getToastStatus().observeForever(toastObserver);
        viewModel.getSizeErrorStatus().observeForever(errorObserver);
        viewModel.getItemsList().observeForever(itemListObserver);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());
    }

    @Test
    public void testInit() {
        viewModel.init();
        verify(toastObserver).onChanged(0);
        verify(itemListObserver).onChanged(benchmark.generateCollectionItems(false));
    }

    @Test
    public void testCalculateTime() {
        viewModel.init();
        viewModel.calculateTime("1000000");
        verify(toastObserver).onChanged(R.string.startingCalc);
    }

    @Test
    public void testStopCalculateTime() {
        viewModel.init();
        viewModel.calculateTime("1000000");
        verify(toastObserver).onChanged(R.string.startingCalc);
        viewModel.calculateTime("1000000");
        verify(toastObserver).onChanged(R.string.stopCalc);
    }

    @Test
    public void testCalculateTimeError() {
        viewModel.calculateTime("abc");
        verify(errorObserver).onChanged(R.string.invalidInput);
    }
}