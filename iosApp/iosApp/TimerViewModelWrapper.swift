import shared
import Combine

class TimerViewModelWrapper: ObservableObject {
    private let viewModel: TimerViewModel

    @Published var reps: [Int64] = []

    private var job: Kotlinx_coroutines_coreJob? = nil

    init(repository: TimerRepository) {
        self.viewModel = TimerViewModel(repository: repository)

        // Watch the CommonFlow from Kotlin
        job = viewModel.reps.watch { [weak self] list in
            if let list = list as? [KotlinLong] {
                self?.reps = list.map { $0.int64Value }
            }
        }
    }

    deinit {
        job?.cancel()
        viewModel.onCleared()
    }

    func addRep(_ rep: Int64) {
        viewModel.addRep(rep: rep)
    }

    func removeRep(_ rep: Int64) {
        viewModel.removeRep(rep: rep)
    }

    func clearReps() {
        viewModel.clearReps()
    }
}
