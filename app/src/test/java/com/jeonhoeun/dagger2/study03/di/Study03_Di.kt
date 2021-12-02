package com.jeonhoeun.dagger2.study03.di

import com.jeonhoeun.dagger2.study03.*
import com.jeonhoeun.dagger2.study03.Target
import dagger.Component
import dagger.MembersInjector
import dagger.Module
import dagger.Provides

/**
 * @Component 는 abstract class 또는 interface 에만 붙일 수 있다.
 * 생성되는 컴포넌트클래스는 Dagger가 앞에 붙는다
 *
 * 속성은 크게
 * mudules : 컴포넌트가 에 속하는 모듈들
 * dependencies : 의존되는 컴포넌트들
 * 이 있다.
 *
 *
 * 컴포넌트 메서드는 2가지가 있다.
 * 1. 프로비전 메서드 : 파라미터없이 리턴값만 있으면 주입된 재료를 반환해주는 프로비전 메소드가 된다
 * 2. 멤버 인젝션 메서드 : 하나의 매개변수를 갖는 메서드를 가지면 멤버 인젝션 메서드가 되고 인젝션 밸류를 리턴도 가능하다.
 * 3. 멤버 인젝터 메서드 : 멤버 인젝터를 리턴하면 멤버 인젝터를 이용하여 인젝션도 가능하다 ( 아직 활용 방안은 모름 )
 *
 * 주입방식은
 * 생성자 주입, 멤버 주입, 메소드 주입이 있는데 메소드 주입은 거의 안쓴다
*/

@Component( modules = [ProvisionAndMemberInjectionModule::class])
interface ProvisionAndMemberInjectionComponent{
    fun getInteger() : Integer // 프로비전 메서드
    fun inject(target:Target) // 멤버 인젝션 메서드
    fun getInjector() : MembersInjector<Target>

    //생성자 주입방식으로 공급하며 생성자의 파라미터는 이 컴포넌트 내부에서 공급되는 재료를 한다
    fun getConstructorSample() : ConstructorSample

    //공급 대상 클래스의 상속관계 관련 예제
    fun injectParent(parent: Parent)
    fun injectMe(me: Me)
    fun injectChild(child:Child)
}

@Module
class ProvisionAndMemberInjectionModule{

    var intValue = Integer(100)

    @Provides
    fun provideInt() = intValue

    @Provides
    fun provideString() = "String"

    @Provides
    fun provideA() = A()

    @Provides
    fun provideB() = B()

    @Provides
    fun provideC() = C()
}

/**
 * @Compponent.Builder 를 통하여 빌더를 정의할 수 있다.
 * 조건리스트
 * 1. 컴포넌트 내에 정의해야됨
 * 2. build() : 컴포넌트 /  타입의 메소드가 있어야됨 (빌드 메서드)
 * 3. 빌드 메서드를 제외한 나머지 메서드는 세터 메서드임
 * 4. modules, dependencies 에 있는것들은 세터로 선언해야됨
 * 5. 세터는 반환타입이 void 또는 빌더이어야 함
 * 6. 세터에 @BindsInstance를 통해 컴포넌트에 인스턴스를 주입할 수 있음
 */
@Component( modules = [ProvisionAndMemberInjectionModule::class])
interface BuilderSampleComponent{
    fun getInt() : Integer
    @Component.Builder
    interface Builder{
        fun setModule( provisionAndMemberInjectionModule: ProvisionAndMemberInjectionModule) : Builder
        fun build() : BuilderSampleComponent
    }
}

@Component( modules = [ProvisionAndMemberInjectionModule::class])
interface FactorySampleComponent{
    fun getInt() : Integer
    @Component.Factory
    interface Factory{
        fun newComponent(provisionAndMemberInjectionModule: ProvisionAndMemberInjectionModule) : FactorySampleComponent
    }
}